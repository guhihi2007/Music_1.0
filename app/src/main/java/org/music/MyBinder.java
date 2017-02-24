package org.music;

import android.os.Binder;

/**
 * Created by Administrator on 2017/2/24.
 */

public class MyBinder extends Binder {
    public MusicService getService() {
        return new MusicService();
    }
}
