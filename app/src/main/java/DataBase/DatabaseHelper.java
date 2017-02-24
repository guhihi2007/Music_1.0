package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/23.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG="MM";
    public static final int Versino= 1;
    public static final String TABLE_NAME="song_list";

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context,String name,int version){
        this(context,name,null,version);
    }

    public DatabaseHelper(Context context,String name){
        this(context,name,Versino);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ( SONG PRIMARY KEY NOT NULL,PATH TEXT NOT NULL)";
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ( ID INTEGER PRIMARY KEY NOT NULL,SONG TEXT NOT NULL,PATH TEXT NOT NULL)";
        db.execSQL(sql);
        Log.v(TAG,"Create DataBase Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
