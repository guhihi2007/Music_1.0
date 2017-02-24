package org.music;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/2/21.
 */
public class FileAdapter extends BaseAdapter {

    public static final String TAG = "MM";
    public static final String SONG_NAME = "SONG";
    public static final String SONG_PATH = "PATH";
    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;
    private Context context;
    private java.util.ArrayList<HashMap<String, String>> list;

    public FileAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        list = Data.ScanFile();
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.songlist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.songlist_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String str_name = list.get(position).get(SONG_NAME);
        final String str_path = list.get(position).get(SONG_PATH);
        viewHolder.textView.setText(str_name);
        Log.v(TAG, "设置到ListView显示");
        Log.v(TAG, str_path);
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send_path = new Intent();
                send_path.setClass(context, MusicService.class);
//                send_path.putExtra(SONG_NAME, str_name);
                send_path.putExtra(SONG_PATH, str_path);
                context.startService(send_path);
                Toast.makeText(context, "正在播放....", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView textView;
    }

}
