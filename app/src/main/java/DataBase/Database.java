package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/23.
 */

public class Database {

    public static final String TAG = "MM";
    public static final String DATABASE_NAME = "Music_DB";
    public static final String TABLE_KEY = "SONG";
    public static final String TABLE_VALUES = "PATH";
    public static final String TABLE_NAME = "song_list";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public void get_DB(Context context) {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        this.context = context;
    }

    public void WriteData(String song_name, String song_path) {
        get_DB(context);
        ContentValues cv = new ContentValues();
        cv.put(TABLE_KEY, song_name);
        cv.put(TABLE_VALUES, song_path);
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        sqLiteDatabase.close();
    }

    public ArrayList<Map<String, String>> get_Song_List() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        String str = "SELECT * FROM " + TABLE_NAME + "";
        if (sqLiteDatabase != null) {
//            Log.v(TAG, "sqLiteDatabase not null");
            Cursor cursor = sqLiteDatabase.rawQuery(str, null);
            Map<String, String> map = new HashMap<>();
            int Column_key = cursor.getColumnIndex(TABLE_KEY);
            int Column_values = cursor.getColumnIndex(TABLE_VALUES);
            while (cursor.moveToNext()) {
                String song_name = cursor.getString(1);
                String song_path = cursor.getString(2);
                map.put(TABLE_KEY, song_name);
                map.put(TABLE_VALUES, song_path);
            }
            cursor.close();
            list.add(map);
        }
        Log.v(TAG, "把数据库拿出的数据map放入List");
        return list;
    }

    public boolean isExists(String song_name) {//判断数据库表中是否有歌曲 song_name;
        boolean isExits = true;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_KEY + "= ?";
        if (sqLiteDatabase != null) {
            Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{song_name});
            while (cursor.moveToNext()) {
                Log.v(TAG, "进入While:找到匹配歌曲");
                return true;
            }
            isExits = false;
        }
        return isExits;
    }

    public void insert_DB(String song_name, String song_path) {
        ContentValues cv = new ContentValues();
        cv.put(song_name, song_path);
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        sqLiteDatabase.close();
    }
}
