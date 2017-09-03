package sopt.client.cleanting.MakeTing.CleanerDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class ReviewlistViewHolder extends RecyclerView.ViewHolder {

    TextView writername;
    TextView writedate;
    TextView writecomment;

    ImageView review_star_img1;
    ImageView review_star_img2;
    ImageView review_star_img3;
    ImageView review_star_img4;
    ImageView review_star_img5;



    public ReviewlistViewHolder(View itemView) {
        super(itemView);

        writername = (TextView)itemView.findViewById(R.id.writername);
        writedate = (TextView)itemView.findViewById(R.id.writedate);
        writecomment = (TextView)itemView.findViewById(R.id.writecomment);

        review_star_img1 = (ImageView)itemView.findViewById(R.id.star_img1);
        review_star_img2 = (ImageView)itemView.findViewById(R.id.star_img2);
        review_star_img3 = (ImageView)itemView.findViewById(R.id.star_img3);
        review_star_img4 = (ImageView)itemView.findViewById(R.id.star_img4);
        review_star_img5 = (ImageView)itemView.findViewById(R.id.star_img5);

    }
}
