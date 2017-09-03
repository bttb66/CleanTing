package sopt.client.cleanting.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class CommunityFilterActivity extends AppCompatActivity {

    private RecyclerView CFrecyclerView;
    private ArrayList<SearchBulletinData> bulletinArrayList;
    private BulletinSearchRecyclerAdapter SrecyclerAdapter;
    private LinearLayoutManager layoutManager2;

    NetworkService service;

    FindBulletinData Data = new FindBulletinData();
    BulletinPostData PostData = new BulletinPostData();
    ArrayList<BulletinCommentData> bulletinCommentDatas = new ArrayList<>();

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_filter);

        service = ApplicationController.getInstance().getNetworkService();

        CFrecyclerView = (RecyclerView) findViewById(R.id.CommunityFilterRecyclerView);
        CFrecyclerView.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        CFrecyclerView.setLayoutManager(layoutManager2);

        bulletinArrayList = new ArrayList<SearchBulletinData>();                         //사용자 정의 데이터를 갖는 arraylist
        Call<SearchBulletinResult> searchBulletinResultCall = service.getSearchMyBulletinResult(loginUserDatas.userId);
        searchBulletinResultCall.enqueue(new Callback<SearchBulletinResult>() {
            @Override
            public void onResponse(Call<SearchBulletinResult> call, Response<SearchBulletinResult> response)
            {
                if(response.isSuccessful())
                {
                    bulletinArrayList = response.body().result;
                    SrecyclerAdapter = new BulletinSearchRecyclerAdapter(bulletinArrayList, clickEvent2);
                    CFrecyclerView.setAdapter(SrecyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<SearchBulletinResult> call, Throwable t) {

            }
        });

    }

    public View.OnClickListener clickEvent2 = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = CFrecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함
            Call<FindBulletinResult> findbulletinresult = service.getFindBulletinResult(bulletinArrayList.get(itemPosition).postId);
            findbulletinresult.enqueue(new Callback<FindBulletinResult>() {
                @Override
                public void onResponse(Call<FindBulletinResult> call, Response<FindBulletinResult> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().message.equals("게시물 상세조회에 성공하였습니다."))
                        {
                            Data = response.body().result;
                            PostData = Data.post;
                            bulletinCommentDatas = Data.comment;

                            Intent intent = new Intent(getApplicationContext(),CommunityBulletinDetailActivity.class);
                            intent.putExtra("post",PostData);
                            intent.putExtra("comment",bulletinCommentDatas);
                            startActivity(intent);
//                            Toast.makeText(getContext(),"상세정보 성공 "+PostData.title,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"조회 실패", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<FindBulletinResult> call, Throwable t)
                {
                    Toast.makeText(getApplicationContext(),"통신 연결 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
