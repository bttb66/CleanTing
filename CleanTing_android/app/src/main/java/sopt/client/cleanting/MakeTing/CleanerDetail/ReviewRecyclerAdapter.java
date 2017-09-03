package sopt.client.cleanting.MakeTing.CleanerDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sopt.client.cleanting.Cleanner.CleanerReviewData;
import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewlistViewHolder> {

    ArrayList<CleanerReviewData> reviews;

    public ReviewRecyclerAdapter(ArrayList<CleanerReviewData> itemdatas) {
        this.reviews = itemdatas;
    }

    @Override
    public ReviewlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        ReviewlistViewHolder viewHolder = new ReviewlistViewHolder(view);             //커스텀 뷰홀더 객체 생성

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewlistViewHolder holder, int position) {

        holder.writername.setText(reviews.get(position).userId);             // userid
        holder.writedate.setText(reviews.get(position).date);
        holder.writecomment.setText(reviews.get(position).content);

        if(reviews.get(position).rating.equals("0"))
        {
            holder.review_star_img1.setImageResource(R.drawable.star_line);
            holder.review_star_img2.setImageResource(R.drawable.star_line);
            holder.review_star_img3.setImageResource(R.drawable.star_line);
            holder.review_star_img4.setImageResource(R.drawable.star_line);
            holder.review_star_img5.setImageResource(R.drawable.star_line);
        }
        if(reviews.get(position).rating.equals("1"))
        {
            holder.review_star_img2.setImageResource(R.drawable.star_line);
            holder.review_star_img3.setImageResource(R.drawable.star_line);
            holder.review_star_img4.setImageResource(R.drawable.star_line);
            holder.review_star_img5.setImageResource(R.drawable.star_line);
        }
        if(reviews.get(position).rating.equals("2"))
        {

            holder.review_star_img3.setImageResource(R.drawable.star_line);
            holder.review_star_img4.setImageResource(R.drawable.star_line);
            holder.review_star_img5.setImageResource(R.drawable.star_line);
        }
        if(reviews.get(position).rating.equals("3"))
        {
            holder.review_star_img4.setImageResource(R.drawable.star_line);
            holder.review_star_img5.setImageResource(R.drawable.star_line);
        }
        if(reviews.get(position).rating.equals("4"))
        {
            holder.review_star_img5.setImageResource(R.drawable.star_line);
        }
    }
    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }
}
