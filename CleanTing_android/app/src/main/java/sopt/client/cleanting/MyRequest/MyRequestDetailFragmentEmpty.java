package sopt.client.cleanting.MyRequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sopt.client.cleanting.MakeTing.MakeTingActivity;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.MyRequest.MyRequestFragment.refreshView;

/**
 * Created by 김지원 on 2017-07-01.
 */

public class MyRequestDetailFragmentEmpty extends Fragment {
    Context context;
    ImageView makeTing;

    public MyRequestDetailFragmentEmpty() {
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.viewpager_my_request_detail_empty,container,false);
        makeTing = (ImageView) layout.findViewById(R.id.my_request_make_ting_btn);
        makeTing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MakeTingActivity.class));
            }
        });

        refreshView = true;
        return layout;
    }
}
