package sopt.client.cleanting.MyRequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Cleanner.CleanerData;
import sopt.client.cleanting.Cleanner.SearchCleanerDetailResult;
import sopt.client.cleanting.MakeTing.MakeTingLocationResultData;
import sopt.client.cleanting.MakeTing.MakeTingRequestResult;
import sopt.client.cleanting.MakeTing.MakeTingRequestResultData;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class MyRequestRecruit extends AppCompatActivity {
    ImageView man1, man2, man3, cleanerImg, star1, star2, star3, star4, star5,
            cond, window, ref, commitBtn;
    TextView manCount, starCount, name,  act, review, career, age, date, time, total1,total2,total3,my_request_recruit_moreview;
    EditText warningEdit;
    MakeTingLocationResultData datas;
    String cleanerId, tingId, phone, area, rate, request;
    CleanerData cleanerData;
    boolean selectCond, selectWindow, selectRef = false;
    NetworkService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_recruit);
        service = ApplicationController.getInstance().getNetworkService();

        my_request_recruit_moreview =(TextView)findViewById(R.id.my_request_recruit_moreview);

        my_request_recruit_moreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MoreViewClean.class);
                startActivity(intent);
            }
        });

        datas = (MakeTingLocationResultData)getIntent().getSerializableExtra("datas");
        cleanerId = datas.cleanerId;
        tingId = datas.tingId;

        man1 = (ImageView)findViewById(R.id.my_request_recruit_man1);
        man2 = (ImageView)findViewById(R.id.my_request_recruit_man2);
        man3 = (ImageView)findViewById(R.id.my_request_recruit_man3);
        cleanerImg = (ImageView)findViewById(R.id.my_request_recruit_cleaner_img);
        star1 = (ImageView)findViewById(R.id.my_request_recruit_star1);
        star2 = (ImageView)findViewById(R.id.my_request_recruit_star2);
        star3 = (ImageView)findViewById(R.id.my_request_recruit_star3);
        star4 = (ImageView)findViewById(R.id.my_request_recruit_star4);
        star5 = (ImageView)findViewById(R.id.my_request_recruit_star5);
        cond = (ImageView)findViewById(R.id.my_request_recruit_condi);
        window = (ImageView)findViewById(R.id.my_request_recruit_window);
        ref = (ImageView)findViewById(R.id.my_request_recruit_ref);
        commitBtn = (ImageView)findViewById(R.id.my_request_recruit_join);

        manCount = (TextView)findViewById(R.id.my_request_recruit_man_count);
        starCount = (TextView)findViewById(R.id.my_request_recruit_star_count);
        name = (TextView)findViewById(R.id.my_request_recruit_cleaner_name);
        act = (TextView)findViewById(R.id.my_request_recruit_activity);
        review = (TextView)findViewById(R.id.my_request_recruit_review);
        career = (TextView)findViewById(R.id.my_request_recruit_career);
        age = (TextView)findViewById(R.id.my_request_recruit_age);
        date = (TextView)findViewById(R.id.my_request_recruit_date);
        time = (TextView)findViewById(R.id.my_request_recruit_time);
        total1 = (TextView)findViewById(R.id.my_request_recruit_total1);
        total2 = (TextView)findViewById(R.id.my_request_recruit_total2);
        total3 = (TextView)findViewById(R.id.my_request_recruit_total3);

        warningEdit = (EditText)findViewById(R.id.my_request_recruit_warning);

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
                    Toast.makeText(MyRequestRecruit.this, "통신 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchCleanerDetailResult> call, Throwable t) {
                Toast.makeText(MyRequestRecruit.this, "서버상태 확인", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        date.setText(datas.date);
        time.setText(datas.startTime +"~" +datas.endTime);
        if(datas.cnt.equals("2")){
            man3.setImageResource(R.drawable.man_line);
            manCount.setText("2명/3명");
        } else if(datas.cnt.equals("1")){
            man2.setImageResource(R.drawable.man_line);
            man3.setImageResource(R.drawable.man_line);
            manCount.setText("1명/3명");
        }

        cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCond == false) {
                    cond.setImageResource(R.drawable.conditioner_yellow);
                    selectCond = true;
                    if (selectWindow) {
                        window.setImageResource(R.drawable.window_gray);
                        selectWindow = false;
                    } else if (selectRef) {
                        ref.setImageResource(R.drawable.refrigerator_gray);
                        selectRef = false;
                    }
                    total1.setText("50,000원");
                    total2.setText("45,000원");
                    total3.setText("37,000원");
                } else if (selectCond) {
                    cond.setImageResource(R.drawable.conditioner_gray);
                    selectCond = false;
                    total1.setText("45,000원");
                    total2.setText("40,000원");
                    total3.setText("32,500원");
                }
            }
        });

        window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectWindow == false) {
                    window.setImageResource(R.drawable.window_yellow);
                    selectWindow = true;
                    if (selectCond) {
                        cond.setImageResource(R.drawable.conditioner_gray);
                        selectCond = false;
                    } else if (selectRef) {
                        ref.setImageResource(R.drawable.refrigerator_gray);
                        selectRef = false;
                    }
                    total1.setText("50,000원");
                    total2.setText("45,000원");
                    total3.setText("37,000원");
                } else if (selectWindow) {
                    window.setImageResource(R.drawable.window_gray);
                    selectWindow = false;
                    total1.setText("45,000원");
                    total2.setText("40,000원");
                    total3.setText("32,500원");
                }
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectRef == false) {
                    ref.setImageResource(R.drawable.refrigerator_yellow);
                    selectRef = true;
                    if (selectCond) {
                        cond.setImageResource(R.drawable.conditioner_gray);
                        selectCond = false;
                    } else if (selectWindow) {
                        window.setImageResource(R.drawable.window_gray);
                        selectWindow = false;
                    }
                    total1.setText("50,000원");
                    total2.setText("45,000원");
                    total3.setText("37,000원");
                } else if (selectRef) {
                    ref.setImageResource(R.drawable.refrigerator_gray);
                    selectRef = false;
                    total1.setText("45,000원");
                    total2.setText("40,000원");
                    total3.setText("32,500원");
                }
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectCond){
                    request = "1";
                } else if(selectWindow){
                    request = "2";
                } else if(selectRef){
                    request = "3";
                } else {
                    request = "0";
                }
                MakeTingRequestResultData makeTingRequestResultData = new MakeTingRequestResultData();
                makeTingRequestResultData.userId = loginUserDatas.userId;
                makeTingRequestResultData.userName = loginUserDatas.name;
                makeTingRequestResultData.request = request;
                makeTingRequestResultData.warning = warningEdit.getText().toString();
                makeTingRequestResultData.price = "30000";

                Call<MakeTingRequestResult> makeTingRequestResultCall = service.getMakeTingRequestResult(datas.tingId, makeTingRequestResultData);
                makeTingRequestResultCall.enqueue(new Callback<MakeTingRequestResult>() {
                    @Override
                    public void onResponse(Call<MakeTingRequestResult> call, Response<MakeTingRequestResult> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MyRequestRecruit.this, response.body().message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(RESULT_OK,intent);
                            finish();
                        } else {
                            Toast.makeText(MyRequestRecruit.this, "통신 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MakeTingRequestResult> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
    }

}
