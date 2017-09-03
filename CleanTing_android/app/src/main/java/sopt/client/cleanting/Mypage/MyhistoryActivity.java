package sopt.client.cleanting.Mypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class MyhistoryActivity extends AppCompatActivity {

    private RecyclerView historyrecyclerView;
    private ArrayList<MyHistoryData> historydatas;
    private HistoryRecyclerViewAdapter historyrecyclerAdapter;
    private LinearLayoutManager layoutManager;

    NetworkService service;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhistory);

        service = ApplicationController.getInstance().getNetworkService();

        historyrecyclerView = (RecyclerView)findViewById(R.id.HistoryRecyclerview);
        historyrecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        historyrecyclerView.setLayoutManager(layoutManager);

        Call<MyhistoryResult> myhistoryResultCall = service.getMyHistoryResult(loginUserDatas.userId);
        myhistoryResultCall.enqueue(new Callback<MyhistoryResult>() {
            @Override
            public void onResponse(Call<MyhistoryResult> call, Response<MyhistoryResult> response) {
                if(response.isSuccessful())
                {
                    historydatas = response.body().ret;
                    historyrecyclerAdapter = new HistoryRecyclerViewAdapter(historydatas);
                    historyrecyclerView.setAdapter(historyrecyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<MyhistoryResult> call, Throwable t) {

            }
        });
    }
}
