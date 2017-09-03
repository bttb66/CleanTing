package sopt.client.cleanting.Community;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-05.
 */

public class MyBulletinListRecylerAdapter extends RecyclerView.Adapter<BulletinViewHolder> {

    ArrayList<SearchBulletinData> SearchbulletinArrayList;
    private final View.OnClickListener clickListener;

    public MyBulletinListRecylerAdapter(ArrayList<SearchBulletinData> itemdatas,View.OnClickListener clickListener) {
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
