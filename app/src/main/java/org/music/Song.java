package org.music;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 */

public class Song implements Serializable{
    private String name;
    private String path;
    private int duration;
    private float size;
    public Song(){}
    public Song(String name,String path,int duration,float size){
        this.name=name;
        this.path=path;
        this.duration=duration;
        this.size=size;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
