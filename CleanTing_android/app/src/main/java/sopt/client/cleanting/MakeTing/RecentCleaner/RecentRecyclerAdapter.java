package sopt.client.cleanting.MakeTing.RecentCleaner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class RecentRecyclerAdapter extends RecyclerView.Adapter<RecentCleanerViewHolder> {

    Context context;
    private final View.OnClickListener clickListener;
    ArrayList<RecentCleanerDataArray> Rcleaners;

    public RecentRecyclerAdapter(Context context,ArrayList<RecentCleanerDataArray> itemdatas,View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.Rcleaners = itemdatas;
        this.context = context;
    }

    @Override
    public RecentCleanerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_cleaner, parent, false);
        RecentCleanerViewHolder viewHolder = new RecentCleanerViewHolder(view);             //커스텀 뷰홀더 객체 생성
        view.setOnClickListener(clickListener);                     // 정의된 클릭이벤트를 달아준다

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecentCleanerViewHolder holder, int position) {

        Glide.with(context).load(Rcleaners.get(position).image).into(holder.R_CleanerImg);// 클리너 사진
        holder.R_CleanerName.setText(Rcleaners.get(position).name);             // 클리너 이름
        holder.R_date.setText(Rcleaners.get(position).date);

        int a = Integer.parseInt(Rcleaners.get(position).rate);
        int b = Integer.parseInt(Rcleaners.get(position).review_cnt);
        int result = 0;
        if(b !=0 )
        {
            result = Math.round(a / b);
        }
        else
            result = 0;

        if(result == 0)
        {
            holder.R_star5.setImageResource(R.drawable.star_line);   // 클리너 별점
            holder.R_star4.setImageResource(R.drawable.star_line);
            holder.R_star3.setImageResource(R.drawable.star_line);
            holder.R_star2.setImageResource(R.drawable.star_line);
            holder.R_star1.setImageResource(R.drawable.star_line);
        }
        if(result == 1)
        {
            holder.R_star5.setImageResource(R.drawable.star_line);   // 클리너 별점
            holder.R_star4.setImageResource(R.drawable.star_line);
            holder.R_star3.setImageResource(R.drawable.star_line);
            holder.R_star2.setImageResource(R.drawable.star_line);
        }
        if(result == 2)
        {
            holder.R_star5.setImageResource(R.drawable.star_line);   // 클리너 별점
            holder.R_star4.setImageResource(R.drawable.star_line);
            holder.R_star3.setImageResource(R.drawable.star_line);
        }
        if(result == 3)
        {
            holder.R_star5.setImageResource(R.drawable.star_line);   // 클리너 별점
            holder.R_star4.setImageResource(R.drawable.star_line);
        }
        if(result == 4)
        {
            holder.R_star5.setImageResource(R.drawable.star_line);   // 클리너 별점
        }
    }

    @Override
    public int getItemCount() {
        return Rcleaners != null ? Rcleaners.size() : 0;
    }
}
