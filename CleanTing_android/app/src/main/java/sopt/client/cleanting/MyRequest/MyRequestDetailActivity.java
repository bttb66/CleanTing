package sopt.client.cleanting.MyRequest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Cleanner.CleanerData;
import sopt.client.cleanting.Cleanner.SearchCleanerDetailResult;
import sopt.client.cleanting.MakeTing.EndTingData;
import sopt.client.cleanting.MakeTing.EndTingResult;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;
import static sopt.client.cleanting.Main.MainActivity.REQUEST_MODIFY_TING;

public class MyRequestDetailActivity extends AppCompatActivity {
    ImageView man1, man2, man3, cleanerImg, star1, star2, star3, star4, star5, callBtn,
            editBtn, cancelBtn;
    TextView manCount, starCount, name,  act, review, career, age, date, time, moreRequest, warning, total1,total2,total3;
    String tingId, cleanerId, userId, request, cnt, phone, area, rate, cleanerImgSrc;
    NetworkService service;
    CleanerData cleanerData;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_detail);
        service = ApplicationController.getInstance().getNetworkService();

        man1 = (ImageView)findViewById(R.id.my_request_detail_man1);
        man2 = (ImageView)findViewById(R.id.my_request_detail_man2);
        man3 = (ImageView)findViewById(R.id.my_request_detail_man3);
        cleanerImg = (ImageView)findViewById(R.id.my_request_detail_cleaner_img);
        star1 = (ImageView)findViewById(R.id.my_request_detail_star1);
        star2 = (ImageView)findViewById(R.id.my_request_detail_star2);
        star3 = (ImageView)findViewById(R.id.my_request_detail_star3);
        star4 = (ImageView)findViewById(R.id.my_request_detail_star4);
        star5 = (ImageView)findViewById(R.id.my_request_detail_star5);
        callBtn = (ImageView)findViewById(R.id.my_request_detail_call_btn);
        editBtn = (ImageView)findViewById(R.id.my_request_detail_edit_btn);
        cancelBtn = (ImageView)findViewById(R.id.my_request_detail_cancel_btn);

        manCount = (TextView)findViewById(R.id.my_request_detail_man_count);
        starCount = (TextView)findViewById(R.id.my_request_detail_star_count);
        name = (TextView)findViewById(R.id.my_request_detail_cleaner_name);
        act = (TextView)findViewById(R.id.my_request_detail_activity);
        review = (TextView)findViewById(R.id.my_request_detail_review);
        career = (TextView)findViewById(R.id.my_request_detail_career);
        age = (TextView)findViewById(R.id.my_request_detail_age);
        date = (TextView)findViewById(R.id.my_request_detail_date);
        time = (TextView)findViewById(R.id.my_request_detail_time);
        moreRequest = (TextView)findViewById(R.id.my_request_detail_request_message);
        warning = (TextView)findViewById(R.id.my_request_detail_warning_message);
        total1 = (TextView)findViewById(R.id.my_request_detail_total1);
        total2 = (TextView)findViewById(R.id.my_request_detail_total2);
        total3 = (TextView)findViewById(R.id.my_request_detail_total3);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = "tel:" + cleanerData.phone;
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });

        cleanerId = getIntent().getStringExtra("cleanerId");
        Log.d("클리너 아이디!!!!!!!!!!!!!!!",cleanerId);
        Call<SearchCleanerDetailResult> searchCleanerDetailResultCall = service.getSearchCleanerDetailResult(cleanerId);
        searchCleanerDetailResultCall.enqueue(new Callback<SearchCleanerDetailResult>() {
            @Override
            public void onResponse(Call<SearchCleanerDetailResult> call, Response<SearchCleanerDetailResult> response) {
                if(response.isSuccessful()){
                    cleanerData = response.body().result.cleaner;
                    age.setText("나이: " +cleanerData.age);
                    name.setText(cleanerData.name +" 클리너");
                    phone = cleanerData.phone;
                    career.setText("경력: " +cleanerData.career +"개월");
                    area = cleanerData.area;
                    rate = cleanerData.rate;
                    if(rate.equals("4")){
                        star5.setImageResource(R.drawable.star_line);
                    } else if(rate.equals("3")){
                        star5.setImageResource(R.drawable.star_line);
                        star4.setImageResource(R.drawable.star_line);
                    } else if(rate.equals("2")){
                        star5.setImageResource(R.drawable.star_line);
                        star4.setImageResource(R.drawable.star_line);
                        star3.setImageResource(R.drawable.star_line);
                    } else if(rate.equals("1")){
                        star5.setImageResource(R.drawable.star_line);
                        star4.setImageResource(R.drawable.star_line);
                        star3.setImageResource(R.drawable.star_line);
                        star2.setImageResource(R.drawable.star_line);
                    } else if(rate.equals("0")){
                        star5.setImageResource(R.drawable.star_line);
                        star4.setImageResource(R.drawable.star_line);
                        star3.setImageResource(R.drawable.star_line);
                        star2.setImageResource(R.drawable.star_line);
                        star1.setImageResource(R.drawable.star_line);
                    }
                    starCount.setText(cleanerData.review_cnt +"명");
                    review.setText("리뷰 " +cleanerData.review_cnt +"회");
                    Glide.with(getApplicationContext()).load(cleanerData.image).into(cleanerImg);
                } else {
                    Toast.makeText(MyRequestDetailActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchCleanerDetailResult> call, Throwable t) {
                Toast.makeText(MyRequestDetailActivity.this, "서버상태 확인", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        tingId = getIntent().getStringExtra("tingId");
        userId = getIntent().getStringExtra("userId");
//        total1.setText(getIntent().getStringExtra("price"));
        request = getIntent().getStringExtra("request");
        if(request.equals("0")){
            moreRequest.setText("추가사항 없음");
        } else if(request.equals("1")){
            moreRequest.setText("에어컨 필터 청소");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        } else if(request.equals("2")){
            moreRequest.setText("창틀 청소");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        } else if(request.equals("3")){
            moreRequest.setText("냉장고 정리");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        }
        warning.setText(getIntent().getStringExtra("warning"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        cnt = getIntent().getStringExtra("cnt");
        manCount.setText(cnt+"명/3명");
        if(cnt.equals("2")){
            man3.setImageResource(R.drawable.man_line);
        } else if(cnt.equals("1")){
            man3.setImageResource(R.drawable.man_line);
            man2.setImageResource(R.drawable.man_line);
        }



        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChangeMyRequestDetail.class);
                intent.putExtra("tingId",tingId);
                intent.putExtra("cleanerId",cleanerId);
                intent.putExtra("userId",userId);
                intent.putExtra("price",getIntent().getStringExtra("price"));
                intent.putExtra("request",request);
                intent.putExtra("date",date.getText().toString());
                intent.putExtra("time",time.getText().toString());
                intent.putExtra("cnt",cnt);
                intent.putExtra("cleanerData",cleanerData);
                startActivityForResult(intent, REQUEST_MODIFY_TING);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 생성
                LayoutInflater dialog = LayoutInflater.from(getApplicationContext());
                final View dialogLayout = dialog.inflate(R.layout.activity_custom_dialog, null);
                final Dialog myDialog = new Dialog(MyRequestDetailActivity.this);

                // 다이얼로그 타이틀제거, 투명
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

//                final TextView title = (TextView)dialogLayout.findViewById(R.id.txt_title);
                final TextView content = (TextView)dialogLayout.findViewById(R.id.txt_content);
                final ImageView cancelBtn = (ImageView)dialogLayout.findViewById(R.id.btn_left);
                final ImageView okBtn = (ImageView)dialogLayout.findViewById(R.id.btn_right);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myDialog.cancel();
                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EndTingData endTingData = new EndTingData();
                        endTingData.userId = loginUserDatas.userId;
                        endTingData.userName = loginUserDatas.name;
                        Call<EndTingResult> endTingResultCall = service.getCancelTingResult(tingId,endTingData);
                        endTingResultCall.enqueue(new Callback<EndTingResult>() {
                            @Override
                            public void onResponse(Call<EndTingResult> call, Response<EndTingResult> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(MyRequestDetailActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    myDialog.dismiss();
                                    finish();
                                } else {
                                    Toast.makeText(MyRequestDetailActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<EndTingResult> call, Throwable t) {
                                Toast.makeText(MyRequestDetailActivity.this, "통신 실패.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                content.setText("취소 하시겠습니까?");
                myDialog.setContentView(dialogLayout);

                myDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        request = data.getStringExtra("request");
        if(request.equals("0")){
            moreRequest.setText("추가사항 없음");
        } else if(request.equals("1")){
            moreRequest.setText("에어컨 필터 청소");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        } else if(request.equals("2")){
            moreRequest.setText("창틀 청소");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        } else if(request.equals("3")){
            moreRequest.setText("냉장고 정리");
            total1.setText("50,000원");
            total2.setText("45,000원");
            total3.setText("37,000원");
        }
        warning.setText(data.getStringExtra("warning"));
    }
}
