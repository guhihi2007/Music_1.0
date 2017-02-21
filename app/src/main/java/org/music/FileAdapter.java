package org.music;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/2/21.
 */
public class FileAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;
    private Context context;

    public FileAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int s = ScanFile.FileName().size();
        return s;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.songlist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.songlist_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String str =(String) ScanFile.FileName().get(position).get("Directory");
        viewHolder.textView.setText(str);
        return convertView;
    }

    public final class ViewHolder {
        public TextView textView;
    }

}
