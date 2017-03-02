package org.music;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MainActivity extends Activity implements InitView, View.OnClickListener {

    public static final String TAG = "MM";
    private TextView main_play_name, main_play_singer;
    private ViewPager viewPager;
    private Button main_play_btn, main_play_next;
    private ImageButton imageButton;
    private RelativeLayout Rltlayout;
    private java.util.ArrayList<HashMap<String, String>> list;
    public static final String SONG_PATH = "PATH";


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
        main_play_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Rltlayout:
                Intent go_play_list = new Intent();
                go_play_list.setClass(this, PlayActivity.class);
                startActivity(go_play_list);
                break;
            case R.id.main_play_btn:
                String str_path = list.get(0).get(SONG_PATH);
                Intent send_path = new Intent();
                send_path.setClass(this, MusicService.class);
                send_path.putExtra(SONG_PATH, str_path);
//                startService(send_path);
                bindService(send_path,connection,BIND_AUTO_CREATE);
                Log.v(TAG,str_path);
                Toast.makeText(this,"播放",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder)service;
            MusicService musicService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}

