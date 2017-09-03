package sopt.client.cleanting.MakeTing.RecentCleaner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class RecentCleanerViewHolder extends RecyclerView.ViewHolder  {

    ImageView R_CleanerImg;
    TextView R_CleanerName;
    TextView R_date;
    ImageView R_star1;
    ImageView R_star2;
    ImageView R_star3;
    ImageView R_star4;
    ImageView R_star5;


    public RecentCleanerViewHolder(View itemView) {
        super(itemView);
        R_CleanerImg = (ImageView)itemView.findViewById(R.id.R_cleanerimg);
        R_CleanerName = (TextView)itemView.findViewById(R.id.R_cleanername);
        R_date = (TextView)itemView.findViewById(R.id.R_date);

        R_star1 = (ImageView)itemView.findViewById(R.id.R_starrate1);
        R_star2 = (ImageView)itemView.findViewById(R.id.R_starrate2);
        R_star3 = (ImageView)itemView.findViewById(R.id.R_starrate3);
        R_star4 = (ImageView)itemView.findViewById(R.id.R_starrate4);
        R_star5 = (ImageView)itemView.findViewById(R.id.R_starrate5);

    }
}
