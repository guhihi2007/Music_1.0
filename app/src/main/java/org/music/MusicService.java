package org.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MusicService extends Service {

    private static final String RECEIVER_ACTION = "Receiver_action";
    public static final String TAG = "MM";
    public static final String SONG_NAME = "SONG";
    public static final String SONG_PATH = "PATH";
    private MediaPlayer mediaPlayer;
    private Callback callback;
    private int total_time;
    private int current;
    private Timer timer = new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Song song = (Song) intent.getSerializableExtra("song");
        String str = song.getPath();
        String name = song.getName();
        Log.v(TAG, "Service获取的歌曲路径:" + str);
        if (str != null)
            play_music(str);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (callback != null && mediaPlayer.isPlaying()) {

                    current = mediaPlayer.getCurrentPosition();
                    callback.Set_Current(current);
                    Log.v(TAG,"serviec-------------------current:"+current+"");
                }
            }
        }, 0, 1000);
        Intent intent1 = new Intent();
        intent1.putExtra("name",name);
        intent1.putExtra("time",total_time);
        intent1.putExtra("play","播放");
        intent1.setAction(RECEIVER_ACTION);
        this.sendBroadcast(intent1);
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "Service执行onBind");
        String str = intent.getStringExtra(SONG_PATH);
        if (str != null)
            play_music(str);
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void play_music(String path) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                Log.v(TAG, "开始播放");
            } else if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
                Log.v(TAG, "停止播放");
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                Log.v(TAG, "开始播放");
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
            }

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        total_time = mediaPlayer.getDuration();
        Log.v(TAG, "播放时长:" + total_time);
        mediaPlayer.setLooping(true);
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}
