package org.music;


import java.io.File;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ScanMusic {
    private Song song;
    public  java.util.ArrayList<Song> Find_Mp3(String root) {
        java.util.ArrayList<Song> list = new java.util.ArrayList<>();
        File file = new File(root);
        File[] files = file.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() && !(files[i].isHidden())) {
                    Find_Mp3(files[i].getAbsolutePath());
                } else if (files[i].isFile() && files[i].getName().endsWith(".mp3") && !(files[i].isHidden())) {
                    String fall_name = files[i].getName();
                    String name = fall_name.substring(0, fall_name.lastIndexOf("."));
                    String path = files[i].getAbsolutePath();
                    float size = files[i].length() / 102400f;
                    song = new Song();
                    song.setName(name);
                    song.setPath(path);
                    song.setSize(size);
                }
                list.add(song);
            }
        }
        return list;
    }
}