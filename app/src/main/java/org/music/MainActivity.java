package org.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MainActivity extends Activity implements InitView, View.OnClickListener {


    private TextView main_play_name, main_play_singer;
    private ViewPager viewPager;
    private Button main_play_btn, main_play_next;
    private ImageButton imageButton;
    private RelativeLayout Rltlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    @Override
    public void findView() {
        viewPager = (ViewPager) findViewById(R.id.main_vp);
        imageButton = (ImageButton) findViewById(R.id.main_play_ib);
        main_play_name = (TextView) findViewById(R.id.main_play_name);
        main_play_singer = (TextView) findViewById(R.id.main_play_singer);
        main_play_btn = (Button) findViewById(R.id.main_play_btn);
        main_play_next = (Button) findViewById(R.id.main_play_next);
        Rltlayout = (RelativeLayout) findViewById(R.id.Rltlayout);
    }

    @Override
    public void setListener() {
        Rltlayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Rltlayout:
                Intent go_play_list = new Intent();
                go_play_list.setClass(this, PlayActivity.class);
                startActivity(go_play_list);
                break;
        }
    }
}

