package org.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PlayActivity extends Activity implements InitView,View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ListView listView, main_lv;
    private SeekBar seekBar;
    private TextView textView;
    private Button last_btn, play_btn, next_btn, play_mode_btn, add_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        findView();
        setListener();
        ScanMusic.ScanFile();
    }

    @Override
    public void findView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        listView = (ListView) findViewById(R.id.play_lv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.textview);
        main_lv = (ListView) findViewById(R.id.main_lv);
        last_btn = (Button) findViewById(R.id.last);
        play_btn = (Button) findViewById(R.id.play);
        next_btn = (Button) findViewById(R.id.next);
        play_mode_btn = (Button) findViewById(R.id.play_mode);
        add_btn = (Button) findViewById(R.id.addfile);

    }

    @Override
    public void setListener() {
        last_btn.setOnClickListener(this);
        play_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        play_mode_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last:

                break;
            case R.id.play:

                break;
            case R.id.next:

                break;
            case R.id.addfile:
                Intent intent = new Intent();
                intent.setClass(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
