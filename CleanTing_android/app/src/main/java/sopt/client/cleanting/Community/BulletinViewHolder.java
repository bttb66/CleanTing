package sopt.client.cleanting.Community;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-06-30.
 */

public class BulletinViewHolder extends RecyclerView.ViewHolder {

    TextView BulletinTitle;
    TextView BulletinDate;
    TextView BulletinTime;
    TextView BullentinContent;
    TextView Bullentinreply;

    public BulletinViewHolder(View itemView) {
        super(itemView);

        BulletinTitle = (TextView)itemView.findViewById(R.id.Bulletin_title);
        BulletinDate = (TextView)itemView.findViewById(R.id.Bulletin_date);
        BullentinContent = (TextView)itemView.findViewById(R.id.Bulletin_content);
        Bullentinreply = (TextView)itemView.findViewById(R.id.Bulletin_reply);
        BulletinTime = (TextView)itemView.findViewById(R.id.Bulletin_time);
    }
}
