package sopt.client.cleanting.Community;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-06-30.
 */

public class BulletinListRecylerAdapter extends RecyclerView.Adapter<BulletinViewHolder>  {

    ArrayList<SearchBulletinData> SearchbulletinArrayList;
    ArrayList<FindAllBulletinData> bulletinArrayList;
    private final View.OnClickListener clickListener;

    public BulletinListRecylerAdapter(ArrayList<FindAllBulletinData> itemdatas,View.OnClickListener clickListener)
    {
        this.clickListener = clickListener;
        this.bulletinArrayList = itemdatas;
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
        holder.BulletinTitle.setText(bulletinArrayList.get(position).title);
        holder.BulletinDate.setText(bulletinArrayList.get(position).date);
        if(bulletinArrayList.get(position).content.length() > 20)
        {
            holder.BullentinContent.setText(bulletinArrayList.get(position).content.substring(0,20).toString()+".........");
        }
        else
        {
            holder.BullentinContent.setText(bulletinArrayList.get(position).content);
        }
        holder.Bullentinreply.setText(bulletinArrayList.get(position).comment_cnt);
        holder.BulletinTime.setText(bulletinArrayList.get(position).time);
    }

    @Override
    public int getItemCount() {
        return bulletinArrayList != null ? bulletinArrayList.size() : 0;
    }
}
