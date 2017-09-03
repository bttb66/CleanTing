package sopt.client.cleanting.Community.Reply;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.Community.BulletinCommentData;
import sopt.client.cleanting.Community.BulletinPostData;
import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class ReplyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<BulletinCommentData> commentDatas;
    BulletinPostData bulletinPostData;
    ArrayList<ReplyData> itemdata;
    Context context;

    public ReplyRecyclerViewAdapter(ArrayList<ReplyData> itemdata,Context context,BulletinPostData bulletinPostData,ArrayList<BulletinCommentData> commentDatas) {
        this.context = context;
        this.itemdata = itemdata;
        this.bulletinPostData = bulletinPostData;
        this.commentDatas = commentDatas;
    }


    public ReplyRecyclerViewAdapter(Context context,BulletinPostData bulletinPostData,ArrayList<BulletinCommentData> commentDatas) {
        this.context = context;
        this.bulletinPostData = bulletinPostData;
        this.commentDatas = commentDatas;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_recyclerview_header,parent,false);
            return new CHeaderViewHolder(v);
        }
        if (viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_recyclerview_item,parent,false);
            return new CBaseViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CHeaderViewHolder)
        {
            CHeaderViewHolder headerViewHolder = (CHeaderViewHolder) holder;
            headerViewHolder.BulletinDetailTitle.setText(bulletinPostData.title.toString());
            headerViewHolder.BulletinDetailContent.setText(bulletinPostData.content.toString());
            headerViewHolder.BulletinDetailDate.setText(bulletinPostData.date.toString());
            headerViewHolder.BulletinDetailTime.setText(bulletinPostData.time.toString());
            headerViewHolder.BulletinDetailReplynum.setText(bulletinPostData.comment_cnt.toString());

            Typeface typeFace2 = Typeface.createFromAsset(context.getAssets(),"NanumSquareR.ttf");
            headerViewHolder.BulletinDetailTitle.setTypeface(typeFace2);
            headerViewHolder.BulletinDetailContent.setTypeface(typeFace2);
            headerViewHolder.BulletinDetailReplynum.setTypeface(typeFace2);
        }
        else if(holder instanceof CBaseViewHolder)
        {
            BulletinCommentData currentitem = commentDatas.get(position-1); // 포지션 재 정리
            CBaseViewHolder baseViewHolder = (CBaseViewHolder) holder;
            baseViewHolder.ReplyContent.setText(currentitem.content);
            baseViewHolder.ReplyDate.setText(currentitem.date);
            baseViewHolder.ReplyTime.setText(currentitem.time);
        }
    }
    @Override
    public int getItemCount() {
        return commentDatas.size() + 1 ;
    }


}
