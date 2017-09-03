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
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Community.Reply.BulletinAddCommentData;
import sopt.client.cleanting.Community.Reply.BulletinAddCommentResult;
import sopt.client.cleanting.Community.Reply.ReplyData;
import sopt.client.cleanting.Community.Reply.ReplyRecyclerViewAdapter;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class CommunityBulletinDetailActivity extends AppCompatActivity {

    ImageView send;

    private RecyclerView recyclerView;
    private ArrayList<ReplyData> itemdata;
    private ReplyRecyclerViewAdapter replyRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    BulletinPostData postData;
    ArrayList<BulletinCommentData> commentDatas;
    BulletinAddCommentData addcomment;
    EditText inputReplyEdit;
    public static Context mContext;

    ArrayList<BulletinCommentData> bulletinCommentDatas = new ArrayList<>();
    FindBulletinData Data = new FindBulletinData();

    NetworkService service;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_bulletin_detail);

        mContext = this;
        final Intent intent = getIntent();
        postData = new BulletinPostData();
        postData = (BulletinPostData)intent.getSerializableExtra("post");
        commentDatas = new ArrayList<>();
        commentDatas = (ArrayList<BulletinCommentData>)intent.getSerializableExtra("comment");

        send = (ImageView)findViewById(R.id.sendimg);
        inputReplyEdit = (EditText)findViewById(R.id.inputReplyEdit);

        recyclerView = (RecyclerView)findViewById(R.id.ReplyRecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        service = ApplicationController.getInstance().getNetworkService();

//        itemdata = new ArrayList<ReplyData>();
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));
//        itemdata.add(new ReplyData("오 나도 살래","2015.05. 01"));

        replyRecyclerViewAdapter = new ReplyRecyclerViewAdapter(itemdata,getApplicationContext(),postData,commentDatas);
        recyclerView.setAdapter(replyRecyclerViewAdapter);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final String str = inputReplyEdit.getText().toString();

                addcomment = new BulletinAddCommentData();
                addcomment.userId = loginUserDatas.userId;
                addcomment.userName = loginUserDatas.name;
                addcomment.content = str;

                final Call<BulletinAddCommentResult> bulletinAddCommentResultCall = service.getBulletinAddCommentResult(postData.postId,addcomment);
                bulletinAddCommentResultCall.enqueue(new Callback<BulletinAddCommentResult>() {
                    @Override
                    public void onResponse(Call<BulletinAddCommentResult> call, Response<BulletinAddCommentResult> response) {
                        if(response.isSuccessful())
                        {
                            replyRecyclerViewAdapter.notifyDataSetChanged();
                            inputReplyEdit.setText("");
                            Toast.makeText(getApplicationContext(),"댓글 등록",Toast.LENGTH_SHORT).show();

                            Call<FindBulletinResult> findbulletinresult = service.getFindBulletinResult(postData.postId);
                            findbulletinresult.enqueue(new Callback<FindBulletinResult>() {
                                @Override
                                public void onResponse(Call<FindBulletinResult> call, Response<FindBulletinResult> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().message.equals("게시물 상세조회에 성공하였습니다."))
                                        {
                                            Data = response.body().result;
                                            bulletinCommentDatas = Data.comment;
                                            postData = Data.post;
                                            //Toast.makeText(getApplicationContext(),bulletinCommentDatas.get(0).content,Toast.LENGTH_SHORT).show();
                                            commentDatas.add(0,bulletinCommentDatas.get(0));
                                            replyRecyclerViewAdapter = new ReplyRecyclerViewAdapter(itemdata,getApplicationContext(),postData,commentDatas);
                                            recyclerView.setAdapter(replyRecyclerViewAdapter);
                                            replyRecyclerViewAdapter.notifyDataSetChanged();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "조회 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<FindBulletinResult> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "통신 연결 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                        }
                    }
                    @Override
                    public void onFailure(Call<BulletinAddCommentResult> call, Throwable t)
                    {
                        Toast.makeText(getApplicationContext(),"통신상태 확인",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
