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
import java.util.List;
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
    private int no = 0;
    private Song song;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean single_song = intent.hasExtra("song");
        boolean list_song = intent.hasExtra("list");
        String name = null;
        Log.v("SS", "Service获取的intent:" + single_song);
        if (single_song) {
             song = (Song) intent.getSerializableExtra("song");
            String str = song.getPath();
            name = song.getName();
            if (str != null)
                play_music(str);
        } else if (list_song) {
            final List list = (List) intent.getSerializableExtra("list");
            song = (Song) list.get(no);
            name = song.getName();
            if (!mediaPlayer.isPlaying()) {
                play_music(song.getPath());
            } else {
                mediaPlayer.reset();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (no == list.size())
                        no = 0;
                    no++;
                    play_music(song.getPath());
                }
            });
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (callback != null && mediaPlayer.isPlaying()) {

                    current = mediaPlayer.getCurrentPosition();
                    callback.Set_Current(current);
                }
            }
        }, 0, 1000);
        Intent intent1 = new Intent();
        intent1.putExtra("name", name);
        intent1.putExtra("time", total_time);
        intent1.putExtra("play", "播放");
        intent1.setAction(RECEIVER_ACTION);
        this.sendBroadcast(intent1);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        Log.v(TAG, "Service执行onBind");
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
//                Log.v(TAG, "开始播放");
            } else if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                Log.v(TAG, "停止播放");
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
//                Log.v(TAG, "开始播放");
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
            }
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        total_time = mediaPlayer.getDuration();
//        Log.v(TAG, "播放时长:" + total_time);
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
