package sopt.client.cleanting.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

public class CommunitySearchActivity extends AppCompatActivity {

    ImageView searchimg;
    EditText search_edit;
    RecyclerView SearchRecyclerView;
    ImageView imgview;
    NetworkService service;
    LinearLayout linearLayout;

    private ArrayList<SearchBulletinData> bulletinArrayList;
    private BulletinSearchRecyclerAdapter BrecyclerAdapter;
    private LinearLayoutManager layoutManager;

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
        setContentView(R.layout.activity_community_search);
        service = ApplicationController.getInstance().getNetworkService();

        searchimg = (ImageView)findViewById(R.id.Bulletin_search_img);
        imgview = (ImageView)findViewById(R.id.center_img);
        search_edit = (EditText)findViewById(R.id.editText);
        linearLayout = (LinearLayout)findViewById(R.id.search_img_linear);

        SearchRecyclerView = (RecyclerView)findViewById(R.id.Search_bulletin_Recyclerview);
        SearchRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        SearchRecyclerView.setLayoutManager(layoutManager);

        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchRecyclerView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
//                imgview.setVisibility(View.GONE);

                String str = search_edit.getText().toString();
                // API바뀌면서 수정된 부분(임시데이터)
                /**승혁 수정 요망*/
                SendSearchBulletinData sendSearchBulletinData = new SendSearchBulletinData();
                sendSearchBulletinData.locationNum = "1";
                // 서버한테 보내기
                Call<SearchBulletinResult> searchBulletinResultCall = service.getSearchBulletinResult(str,sendSearchBulletinData);
                searchBulletinResultCall.enqueue(new Callback<SearchBulletinResult>() {
                    @Override
                    public void onResponse(Call<SearchBulletinResult> call, Response<SearchBulletinResult> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().message.equals("Succeed in searching a post")){
//                                bulletinArrayList = new ArrayList<SearchBulletinData>();
                                bulletinArrayList = response.body().result;

                                if(bulletinArrayList.size() == 0)
                                {
                                    Toast.makeText(getApplicationContext(),"일치하는 것 없음",Toast.LENGTH_SHORT).show();
                                }

                                BrecyclerAdapter = new BulletinSearchRecyclerAdapter(bulletinArrayList,clickEvent);
                                SearchRecyclerView.setAdapter(BrecyclerAdapter);
                                SearchRecyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"일치 하는거 없음",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchBulletinResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = SearchRecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함
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
