package sopt.client.cleanting.Community.Reply;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class CHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView BulletinDetailTitle;
    TextView BulletinDetailDate;
    TextView BulletinDetailContent;
    TextView BulletinDetailReplynum;
    TextView BulletinDetailTime;

    public CHeaderViewHolder(View itemView) {
        super(itemView);
        BulletinDetailTitle = (TextView)itemView.findViewById(R.id.BulletinDetail_titile);
        BulletinDetailDate = (TextView)itemView.findViewById(R.id.BulletinDetail_date);
        BulletinDetailContent = (TextView)itemView.findViewById(R.id.BulletinDetail_content);
        BulletinDetailReplynum = (TextView)itemView.findViewById(R.id.BulletinDetail_replynum);
        BulletinDetailTime = (TextView)itemView.findViewById(R.id.BulletinDetail_time);
    }
}
