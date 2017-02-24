package org.music;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ScanMusic {

    public static final String SONG_NAME = "SONG";
    public static final String SONG_PATH = "PATH";
//    private static ScanMusic data;
//    private ScanMusic() {
//    }
//
//    public static ScanMusic getDataInstance() {
//        if (data == null) {
//            data = new ScanMusic();
//        }
//        return data;
//    }

    public static java.util.ArrayList<HashMap<String, String>> ScanFile() {
        java.util.ArrayList<HashMap<String, String>> list = new java.util.ArrayList<>();
        String root = "";
        String Path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        Path = root + "/Music";
        File file = new File(Path);
        File[] files = file.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                HashMap<String, String> map = new HashMap<>();
                if (files[i].isDirectory() && ! ( files[i].isHidden()) ){
                    ScanFile();
                }
                if (files[i].isFile() && files[i].getName().endsWith(".mp3") && ! ( files[i].isHidden())) {
                    String name = files[i].getName();
                    String path = files[i].getAbsolutePath();
                    map.put(SONG_NAME, name);
                    map.put(SONG_PATH, path);
                }
                list.add(map);
            }
        }
        return list;
    }
}