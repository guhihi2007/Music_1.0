package org.music;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.UnicodeSetSpanner;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MusicService extends Service {

    public static final String TAG = "MM";
    public static final String SONG_NAME = "SONG";
    public static final String SONG_PATH = "PATH";
    private MediaPlayer mediaPlayer;
    private boolean Compelete;
    private Callback callback;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String str = intent.getStringExtra(SONG_PATH);
        final String name = intent.getStringExtra(SONG_NAME);
        Log.v(TAG, "Service获取的歌曲路径:" + str);
        if (str != null)
            play_music(str);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                callback.Set_btn_Status("暂停",name);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "执行onBind");
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
        mediaPlayer.start();
//        mediaPlayer.setLooping(true);
    }

    public void setCallback(Callback callback){
        this.callback=callback;
    }
    public boolean isCompelete() {

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Compelete = true;
            }
        });
        return Compelete;
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
