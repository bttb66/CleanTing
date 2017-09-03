package sopt.client.cleanting.MakeTing.ListCleaner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class ListCleanerViewHolder extends RecyclerView.ViewHolder {

    public ImageView ListCleanerimg;
    public TextView ListCleanername;
    public TextView ListCleanerhistory;
    public TextView ListCleanercomment;
    public ImageView ListCleanerrate1;
    public ImageView ListCleanerrate2;
    public ImageView ListCleanerrate3;
    public ImageView ListCleanerrate4;
    public ImageView ListCleanerrate5;

    public ListCleanerViewHolder(View itemView) {
        super(itemView);

        ListCleanerimg = (ImageView)itemView.findViewById(R.id.List_cleanerimg);
        ListCleanername = (TextView)itemView.findViewById(R.id.List_cleanername);
        ListCleanerhistory = (TextView)itemView.findViewById(R.id.List_cleanerhistory);
        ListCleanercomment = (TextView)itemView.findViewById(R.id.List_cleanercomment);

        ListCleanerrate1 = (ImageView)itemView.findViewById(R.id.List_clenaerstar1);
        ListCleanerrate2 = (ImageView)itemView.findViewById(R.id.List_clenaerstar2);
        ListCleanerrate3 = (ImageView)itemView.findViewById(R.id.List_clenaerstar3);
        ListCleanerrate4 = (ImageView)itemView.findViewById(R.id.List_clenaerstar4);
        ListCleanerrate5 = (ImageView)itemView.findViewById(R.id.List_clenaerstar5);
    }
}
