package org.music;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ScanFile {

    public static java.util.ArrayList<HashMap<String, Object>> FileName() {
        String s="";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            s = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        File file = new File(s+"/A");
        File[] files = file.listFiles();
        java.util.ArrayList<HashMap<String, Object>> list = new java.util.ArrayList<>();
        if (files.length > 0) {
            Log.v("File个数", "" + files.length + "");
            for (int i = 0; i < files.length; i++) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                if (files[i].isDirectory() && !files[i].isHidden()) {
                    Log.v("File", "----------->>>" + files[i].getName());
                    hashMap.put("Directory", files[i].getName());
                } else if (files[i].isFile()) {
                    Log.v("File", "文件：" + files[i].getName());
                    hashMap.put("Directory", files[i].getName());
                }
                list.add(hashMap);
            }
        }
        return list;
    }

    public static File[] FileArray(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        return files;
    }
}
//    public static void FileName(String Path) {
//        File[] files = FileArray(Path);
//        if (files.length > 0) {
//            Log.v("File个数", "" + files.length + "");
//            Map<String,Object> hashMap= new HashMap<String,Object>();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isDirectory() && !files[i].isHidden()) {
//                    Log.v("File", "----------->>>" + files[i].getName());
//                    hashMap.put("Directory",files[i].getName());
//                } else if (files[i].isFile()) {
//                    Log.v("File", "文件：" + files[i].getName());
//                    hashMap.put("File",files[i].getName());
//                }
//            }
//        }
//    }