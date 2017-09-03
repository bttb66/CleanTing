package sopt.client.cleanting.Mypage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.R;
import sopt.client.cleanting.ViewHolder.MyCleanHistoryViewHolder;

/**
 * Created by glgld on 2017-06-29.
 */

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyCleanHistoryViewHolder> {

    ArrayList<MyHistoryData> historydatas;

    public HistoryRecyclerViewAdapter(ArrayList<MyHistoryData> itemdatas)
    {
        this.historydatas = itemdatas;
    }

    @Override
    public MyCleanHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleanhistory_recycler, parent, false);
        MyCleanHistoryViewHolder viewHolder = new MyCleanHistoryViewHolder(view);             //커스텀 뷰홀더 객체 생성

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyCleanHistoryViewHolder holder, final int position)
    {
        holder.cleanername_tv.setText(historydatas.get(position).name + " 클리너");
        holder.cleandate_tv.setText("청소일시 : "+ historydatas.get(position).date);
        holder.cleantime_tv.setText("청소시간 : "+ historydatas.get(position).startTime +" ~ " + historydatas.get(position).endTime);

        holder.rate_btn.setOnClickListener(new View.OnClickListener() { //평가하기 버튼
            @Override
            public void onClick(View v) {
                String str = historydatas.get(position).cleanerId;
                Intent intent = new Intent(v.getContext(),RatingActivity.class);
                intent.putExtra("cleanerId",str);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historydatas != null ? historydatas.size() : 0;
    }
}
