package sopt.client.cleanting.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by 김지원 on 2017-06-27.
 */

public class MyLocationViewHolder extends RecyclerView.ViewHolder{
    public TextView myLocationDay, myLocationTime;
    public ImageView myLocationImg, myLocationMember1, myLocationMember2, myLocationMember3;

    public MyLocationViewHolder(View itemView) {
        super(itemView);
        myLocationDay = (TextView)itemView.findViewById(R.id.my_location_day);
        myLocationTime = (TextView)itemView.findViewById(R.id.my_location_time);
        myLocationImg = (ImageView)itemView.findViewById(R.id.my_location_img);
        myLocationMember1 = (ImageView)itemView.findViewById(R.id.my_location_man1);
        myLocationMember2 = (ImageView)itemView.findViewById(R.id.my_location_man2);
        myLocationMember3 = (ImageView)itemView.findViewById(R.id.my_location_man3);

    }
}
