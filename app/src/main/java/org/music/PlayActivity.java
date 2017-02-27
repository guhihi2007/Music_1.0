package org.music;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PlayActivity extends Activity implements InitView, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ListView play_lv, main_lv;
    private SeekBar seekBar;
    private TextView textView;
    private Button  play_btn, A, B;
    public static final String TAG = "MM";
    public static final String SONG_NAME = "SONG";
    public static final String SONG_PATH = "PATH";
    private java.util.ArrayList<HashMap<String, String>> list;
    private MusicService musicService;
    private String previous_song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        findView();
        setListener();
        list = ScanMusic.ScanFile(1);
        bindService(new Intent(this, MusicService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
                musicService = myBinder.getService();
                musicService.setCallback(new Callback() {
                    @Override
                    public void Set_btn_Status(String isplay,String song_name) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("1",isplay);
                        bundle.putString("2",song_name);
                        Log.v(TAG,"写入bundle");
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }
                });
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
//        bindService(new Intent(this, MusicService.class), new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                MusicService.MyBinder myBinder = (MusicService.MyBinder)service;
//                musicService = myBinder.getService();
//                musicService.setCallback(new Callback() {
//                    @Override
//                    public void Set_btn_Status(String isplay) {
//                        Message message = new Message();
//                        message.obj=isplay;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        },BIND_AUTO_CREATE);
        super.onStart();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            String btn=bundle.getString("1");
            String name=bundle.getString("2");
            Log.v(TAG,"拿出bundle");
            play_btn.setText(btn);
            textView.setText(name);
        }
    };

    @Override
    public void findView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        play_lv = (ListView) findViewById(R.id.play_lv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.textview);
        main_lv = (ListView) findViewById(R.id.main_lv);
//        last_btn = (Button) findViewById(R.id.last);
        play_btn = (Button) findViewById(R.id.play);
//        next_btn = (Button) findViewById(R.id.next);
//        play_mode_btn = (Button) findViewById(R.id.play_mode);
//        add_btn = (Button) findViewById(R.id.addfile);
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
    }

    @Override
    public void setListener() {
        play_btn.setOnClickListener(this);
        play_lv.setAdapter(new FileAdapter(this, 1));
        A.setOnClickListener(this);
        B.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.A:
                play_lv.setAdapter(new FileAdapter(this, 1));
                break;
            case R.id.play:
                MediaPlayer mediaPlayer = musicService.getMediaPlayer();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play_btn.setText("播放");
                } else {
                    Log.v(TAG, "media正在播放");
                    mediaPlayer.start();
                    play_btn.setText("暂停");
                }
                break;
            case R.id.B:
                play_lv.setAdapter(new FileAdapter(this, 2));
                break;
//            case R.id.addfile:
//                ScanMusic.ScanFile();
//                Intent intent = new Intent();
//                intent.setClass(this, AddActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
