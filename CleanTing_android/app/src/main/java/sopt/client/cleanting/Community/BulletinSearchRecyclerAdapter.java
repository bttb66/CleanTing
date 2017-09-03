package sopt.client.cleanting.Community;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-04.
 */

public class BulletinSearchRecyclerAdapter extends RecyclerView.Adapter<BulletinViewHolder> {

    ArrayList<SearchBulletinData> SearchbulletinArrayList;
    private final View.OnClickListener clickListener;


    public BulletinSearchRecyclerAdapter(ArrayList<SearchBulletinData> itemdatas,View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.SearchbulletinArrayList = itemdatas;
    }

    @Override
    public BulletinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulletin_list,parent, false);
        BulletinViewHolder viewHolder = new BulletinViewHolder(view);             //커스텀 뷰홀더 객체 생성
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BulletinViewHolder holder, int position) {
        holder.BulletinTitle.setText(SearchbulletinArrayList.get(position).title);
        holder.BulletinDate.setText(SearchbulletinArrayList.get(position).date);

        if(SearchbulletinArrayList.get(position).content.length() > 20)
        {
            holder.BullentinContent.setText(SearchbulletinArrayList.get(position).content.substring(0,20).toString()+".........");
        }
        else
        {
            holder.BullentinContent.setText(SearchbulletinArrayList.get(position).content);
        }
        holder.Bullentinreply.setText(SearchbulletinArrayList.get(position).comment_cnt);
        holder.BulletinTime.setText(SearchbulletinArrayList.get(position).time);
    }

    @Override
    public int getItemCount() {
        return SearchbulletinArrayList != null ? SearchbulletinArrayList.size() : 0;
    }
}
