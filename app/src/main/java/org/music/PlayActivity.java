package org.music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PlayActivity extends Activity implements InitView, View.OnClickListener {

    public static final String TAG = "MM";
    private static final String RECEIVER_ACTION="Receiver_action";
    private TextView song_name_textView, total_time_textView, start_time_textView;
    private java.util.ArrayList<Song> list;
    private MusicService musicService;
    private DrawerLayout drawerLayout;
    private MediaPlayer mediaPlayer;
    private Service_Receiver service_receiver;
    private Button play_btn, A, B;
    private ListView play_lv;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        findView();
//        list=Start_Thread_scanFile("/A");
        setListener();
//        Log.v(TAG,"主线程:"+list+"");
        Start_Bindservice();
        service_receiver = new Service_Receiver();
        registerReceiver(service_receiver);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            int current_time=msg.what;
            String current=getTime(current_time);
            Log.v(TAG,"Handler内current："+current+"");
            start_time_textView.setText(current);
            seekBar.setProgress(current_time/1000);

        }
    };

    private void Start_Bindservice() {
        bindService(new Intent(this, MusicService.class),connection , BIND_AUTO_CREATE);

    }
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, IBinder service) {
            final MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            musicService = myBinder.getService();
            musicService.setCallback(new Callback() {
                @Override
                public void Set_Current(int current) {
                    Message message= new Message();
                    message.what=current;
                    handler.sendMessage(message);
                    Log.v(TAG,"Connect--------message.what:"+current+"");
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void findView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        play_lv = (ListView) findViewById(R.id.play_lv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        song_name_textView = (TextView) findViewById(R.id.textview);
        play_btn = (Button) findViewById(R.id.play);
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
        total_time_textView =(TextView)findViewById(R.id.end_time);
        start_time_textView = (TextView)findViewById(R.id.start_time);
    }

    @Override
    public void setListener() {
        play_btn.setOnClickListener(this);
//        play_lv.setAdapter(new FileAdapter(this, list));
        play_lv.setAdapter(new FileAdapter(this, Start_Thread_scanFile("/A")));
        A.setOnClickListener(this);
        B.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.A:
                play_lv.setAdapter(new FileAdapter(this, Start_Thread_scanFile("/A")));
//        play_lv.setAdapter(new FileAdapter(this, list);
                break;
            case R.id.play:
                mediaPlayer = musicService.getMediaPlayer();
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
                play_lv.setAdapter(new FileAdapter(this, Start_Thread_scanFile("/B")));
//        play_lv.setAdapter(new FileAdapter(this, list);
                break;
        }
    }
    private ArrayList<Song> Start_Thread_scanFile(String pant) {
        String root = "";
        ScanMusic scanMusic = new ScanMusic();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        list = scanMusic.Find_Mp3(root + pant);
        return list;
    }
    private void registerReceiver(Service_Receiver service_receiver){
        Log.v(TAG,"注册-----------------Receiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION);
        registerReceiver(service_receiver,intentFilter);
    }

    private class Service_Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG,"onReceive--------------执行");
            String song_name=intent.getStringExtra("name");
            int i=intent.getIntExtra("time",0);
            song_name_textView.setText(song_name);
            total_time_textView.setText(getTime(i));
            seekBar.setMax(i/1000);
            play_btn.setText("暂停");
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        unregisterReceiver(service_receiver);
        super.onDestroy();
    }

    public String getTime(int time) {
        java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("mm:ss");
        return format1.format(time);

    }
}
