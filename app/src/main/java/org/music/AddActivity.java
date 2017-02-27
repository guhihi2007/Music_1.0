package org.music;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */

public class AddActivity extends Activity implements InitView {
    private ListView listView;
    public static final String TAG = "MM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        findView();
        setListener();

    }

    @Override
    public void findView() {
        listView = (ListView) findViewById(R.id.search_list);
    }

    @Override
    public void setListener() {
//        listView.setAdapter(new FileAdapter(this));
    }


}
