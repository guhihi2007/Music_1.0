package org.music;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
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

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String str = intent.getStringExtra(SONG_PATH);
        Log.v(TAG, "Service获取的歌曲路径:" + str);
        play_music(str);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public void play_music(String path) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                Log.v(TAG, "开始播放");
            } else if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                Log.v(TAG, "开始播放");
            } else {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                Log.v(TAG, "开始播放");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

}
