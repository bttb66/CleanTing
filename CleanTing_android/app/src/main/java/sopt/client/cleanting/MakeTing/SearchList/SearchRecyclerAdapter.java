package sopt.client.cleanting.MakeTing.SearchList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sopt.client.cleanting.Cleanner.SearchLocationCleanerData;
import sopt.client.cleanting.MakeTing.ListCleaner.ListCleanerViewHolder;
import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-07-03.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<ListCleanerViewHolder> {

    private final View.OnClickListener clickListener;
    private final ArrayList<SearchLocationCleanerData> Lcleaners;
    Context context;

    public SearchRecyclerAdapter(Context context,ArrayList<SearchLocationCleanerData> itemdatas,View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.Lcleaners = itemdatas;
        this.context = context;
    }

    @Override
    public ListCleanerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_list, parent, false);
        ListCleanerViewHolder viewHolder = new ListCleanerViewHolder(view);             //커스텀 뷰홀더 객체 생성
        view.setOnClickListener(clickListener);                     // 정의된 클릭이벤트를 달아준다

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListCleanerViewHolder holder, int position) {

        Glide.with(context).load(Lcleaners.get(position).image).into(holder.ListCleanerimg);

        holder.ListCleanername.setText(Lcleaners.get(position).name+" 클리너");             // 클리너 이름
        holder.ListCleanerhistory.setText("청소 경력 : "+ Lcleaners.get(position).career + "개월");
        holder.ListCleanercomment.setText("후기 : "+ Lcleaners.get(position).review_cnt);

        int a = Integer.parseInt(Lcleaners.get(position).rate);
        int b = Integer.parseInt(Lcleaners.get(position).review_cnt);
        int result = 0;
        if(b !=0 )
        {
            result = Math.round(a / b);
        }
        else
            result = 0;

        if(result == 0)
        {
            holder.ListCleanerrate1.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate2.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate3.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate4.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate5.setImageResource(R.drawable.star_line);
        }
        if(result == 1)
        {
            holder.ListCleanerrate2.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate3.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate4.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate5.setImageResource(R.drawable.star_line);
        }
        if(result == 2)
        {
            holder.ListCleanerrate3.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate4.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate5.setImageResource(R.drawable.star_line);
        }
        if(result == 3)
        {
            holder.ListCleanerrate4.setImageResource(R.drawable.star_line);
            holder.ListCleanerrate5.setImageResource(R.drawable.star_line);
        }
        if(result == 4)
        {
            holder.ListCleanerrate5.setImageResource(R.drawable.star_line);
        }
    }

    @Override
    public int getItemCount() {
        return Lcleaners != null ? Lcleaners.size() : 0;
    }
}
