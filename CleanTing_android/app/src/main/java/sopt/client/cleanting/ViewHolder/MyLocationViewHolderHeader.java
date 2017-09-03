package sopt.client.cleanting.ViewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sopt.client.cleanting.R;

/**
 * Created by 김지원 on 2017-06-28.
 */

public class MyLocationViewHolderHeader extends RecyclerView.ViewHolder{
    public TextView myLocationBtn;
    public static ViewPager viewPager;

    public TextView tv18;
    public  TextView tv28;

    public MyLocationViewHolderHeader(View itemView) {
        super(itemView);
        viewPager = (ViewPager)itemView.findViewById(R.id.my_request_view_pager);

        tv18 = (TextView)itemView.findViewById(R.id.textview18);
        tv28 = (TextView)itemView.findViewById(R.id.textview28);
    }
}
