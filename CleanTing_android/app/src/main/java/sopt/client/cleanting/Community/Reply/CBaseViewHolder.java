package sopt.client.cleanting.Community.Reply;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class CBaseViewHolder extends RecyclerView.ViewHolder {

    TextView ReplyContent;
    TextView ReplyDate;
    TextView ReplyTime;

    public CBaseViewHolder(View itemView) {
        super(itemView);

        ReplyContent = (TextView)itemView.findViewById(R.id.ReplyContent);
        ReplyDate = (TextView)itemView.findViewById(R.id.ReplyDate);
        ReplyTime = (TextView)itemView.findViewById(R.id.ReplyTime);
    }
}
