package org.music;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import DataBase.Database;

/**
 * Created by Administrator on 2017/2/27.
 */

public class Test extends Activity {
    public static final String TAG = "MM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Database database = new Database();
//        java.util.ArrayList<HashMap<String, String>> list= ScanMusic.Find_Mp3(1);
        ListView listView = (ListView)findViewById(R.id.listview);
        TextView textView = (TextView)findViewById(R.id.songlist_tv);
//        listView.setAdapter(new FileAdapter(this,1,list));
        database.get_DB(this);
//        database.WriteData(list.get(0).get("SONG"),list.get(0).get("PATH"));
        boolean f= database.isExists("Cry on My Shoulder_Deutschland Sucht den Superstar.mp3");
        Log.v(TAG,""+f+"");
    }
}
