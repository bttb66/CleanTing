package sopt.client.cleanting.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-06-29.
 */

public class MyCleanHistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView cleanername_tv;
    public TextView cleandate_tv;
    public TextView cleantime_tv;
    public ImageView rate_btn;

    public MyCleanHistoryViewHolder(View itemView) {
        super(itemView);

        cleanername_tv = (TextView)itemView.findViewById(R.id.H_cleanername);
        cleandate_tv = (TextView)itemView.findViewById(R.id.H_cleandate);
        cleantime_tv = (TextView)itemView.findViewById(R.id.H_cleantime);
        rate_btn = (ImageView)itemView.findViewById(R.id.rate_btn);
    }
}
