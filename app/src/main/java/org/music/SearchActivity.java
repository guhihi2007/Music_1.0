package org.music;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SearchActivity extends Activity implements InitView {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        Log.v("File", "onCreate");
        findView();
        setListener();
//        findFile();
    }

    @Override
    public void findView() {
        listView = (ListView) findViewById(R.id.search_list);
    }

    @Override
    public void setListener() {
        listView.setAdapter(new FileAdapter(this));
    }

//    public void findFile() {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String s = Environment.getExternalStorageDirectory().getAbsolutePath();
////            String s1 = s + "/Music";
////            Log.v("File>>>>>", s);
//        }
//    }


//    public void FileName(String Path) {
//        File file = new File(Path);
//        File[] files = file.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].isDirectory()) {
//                Log.v("File", "============" + files[i].getName());
//                FileName(files[i].getAbsolutePath());
//            } else if (files[i].isFile()) {
//                Log.v("File","--->"+ files[i].getName());
//            }
//        }
//    }
}
