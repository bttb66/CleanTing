package sopt.client.cleanting.Alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sopt.client.cleanting.R;

/**
 * Created by 진영 on 2017-07-02.
 */

public class AlarmAdapter extends BaseAdapter {

    private ArrayList<ListItem> listViewItemList = new ArrayList<ListItem>();

    public AlarmAdapter(){

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String text_item, String text_time){
        ListItem item = new ListItem();

        item.setStr(text_item);
        item.setTime(text_time);

        listViewItemList.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final Context context= parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.activity_listitem,parent,false);
        }

        TextView item_text=(TextView)convertView.findViewById(R.id.item_text);
        TextView item_time=(TextView)convertView.findViewById(R.id.item_time);

        ListItem listitem= listViewItemList.get(position);

        item_text.setText(listitem.getStr());
        item_time.setText(listitem.getTime());

        return convertView;
    }
}
