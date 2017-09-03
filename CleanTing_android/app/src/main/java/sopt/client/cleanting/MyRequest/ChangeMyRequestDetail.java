package sopt.client.cleanting.MyRequest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

public class ChangeMyRequestDetail extends AppCompatActivity {
    ImageView man1, man2, man3, cleanerImg, star1, star2, star3, star4, star5, callBtn,
    cond, window, ref, commitBtn;
    TextView manCount, starCount, name,  act, review, career, age, date, time, total1,total2,total3;
    String tingId, cleanerId, userId, request, cnt, phone, area, rate, price;
    EditText warningEdit;
    CleanerData cleanerData;
    boolean selectCond, selectWindow, selectRef = false;
    NetworkService service;
    TextView moreview_clean;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_request_detail);
        service = ApplicationController.getInstance().getNetworkService();

        man1 = (ImageView)findViewById(R.id.change_my_request_detail_man1);
        man2 = (ImageView)findViewById(R.id.change_my_request_detail_man2);
        man3 = (ImageView)findViewById(R.id.change_my_request_detail_man3);
        cleanerImg = (ImageView)findViewById(R.id.change_my_request_detail_cleaner_img);
        star1 = (ImageView)findViewById(R.id.change_my_request_detail_star1);
        star2 = (ImageView)findViewById(R.id.change_my_request_detail_star2);
        star3 = (ImageView)findViewById(R.id.change_my_request_detail_star3);
        star4 = (ImageView)findViewById(R.id.change_my_request_detail_star4);
        star5 = (ImageView)findViewById(R.id.change_my_request_detail_star5);
        callBtn = (ImageView)findViewById(R.id.change_my_request_detail_call_btn);
        cond = (ImageView)findViewById(R.id.change_my_request_detail_condi);
        window = (ImageView)findViewById(R.id.change_my_request_detail_window);
        ref = (ImageView)findViewById(R.id.change_my_request_detail_ref);
        commitBtn = (ImageView)findViewById(R.id.change_my_request_detail_btn);

        manCount = (TextView)findViewById(R.id.change_my_request_detail_man_count);
        starCount = (TextView)findViewById(R.id.change_my_request_detail_star_count);
        name = (TextView)findViewById(R.id.change_my_request_detail_cleaner_name);
        act = (TextView)findViewById(R.id.change_my_request_detail_activity);
        review = (TextView)findViewById(R.id.change_my_request_detail_review);
        career = (TextView)findViewById(R.id.change_my_request_detail_career);
        age = (TextView)findViewById(R.id.change_my_request_detail_age);
        date = (TextView)findViewById(R.id.change_my_request_detail_date);
        time = (TextView)findViewById(R.id.change_my_request_detail_time);
        total1 = (TextView)findViewById(R.id.change_my_request_detail_total1);
        total2 = (TextView)findViewById(R.id.change_my_request_detail_total2);
        total3 = (TextView)findViewById(R.id.change_my_request_detail_total3);

        moreview_clean=(TextView)findViewById(R.id.moreview_clean);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = "tel:" + cleanerData.phone;
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });

        moreview_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MoreViewClean.class);
                startActivity(intent);
            }
        });

        cleanerId = getIntent().getStringExtra("cleanerId");
        tingId = getIntent().getStringExtra("tingId");
        userId = getIntent().getStringExtra("userId");
        price = getIntent().getStringExtra("price");
//        total1.setText(price +"원");
        request = getIntent().getStringExtra("request");
        if(request.equals("1")){
            cond.setImageResource(R.drawable.conditioner_yellow);
            selectCond = true;
        } else if(request.equals("2")){
            window.setImageResource(R.drawable.window_yellow);
            selectWindow = true;
        } else if(request.equals("3")){
            ref.setImageResource(R.drawable.refrigerator_yellow);
            selectRef = true;
        }
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        cnt = getIntent().getStringExtra("cnt");
        manCount.setText(cnt+"명/3명");
        if(cnt.equals("2")){
            man1.setImageResource(R.drawable.man_line_o);
        } else if(cnt.equals("1")){
            man1.setImageResource(R.drawable.man_line_o);
            man2.setImageResource(R.drawable.man_line_o);
        }
        cleanerData = (CleanerData)getIntent().getSerializableExtra("cleanerData");
        Glide.with(getApplicationContext()).load(cleanerData.image).into(cleanerImg);
        cleanerImg.setAlpha(88);
        age.setText("나이: " +cleanerData.age);
        name.setText(cleanerData.name +" 클리너");
        phone = cleanerData.phone;
        career.setText("경력: " +cleanerData.career +"개월");
        area = cleanerData.area;
        rate = cleanerData.rate;
        if(rate.equals("4")){
            star5.setImageResource(R.drawable.star_line_o);
        } else if(rate.equals("3")){
            star5.setImageResource(R.drawable.star_line_o);
            star4.setImageResource(R.drawable.star_line_o);
        } else if(rate.equals("2")){
            star5.setImageResource(R.drawable.star_line_o);
            star4.setImageResource(R.drawable.star_line_o);
            star3.setImageResource(R.drawable.star_line_o);
        } else if(rate.equals("1")){
            star5.setImageResource(R.drawable.star_line_o);
            star4.setImageResource(R.drawable.star_line_o);
            star3.setImageResource(R.drawable.star_line_o);
            star2.setImageResource(R.drawable.star_line_o);
        } else if(rate.equals("0")){
            star5.setImageResource(R.drawable.star_line_o);
            star4.setImageResource(R.drawable.star_line_o);
            star3.setImageResource(R.drawable.star_line_o);
            star2.setImageResource(R.drawable.star_line_o);
            star1.setImageResource(R.drawable.star_line_o);
        }
        starCount.setText(cleanerData.review_cnt +"명");
        review.setText("리뷰 " +cleanerData.review_cnt +"회");

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

        warningEdit = (EditText)findViewById(R.id.change_my_request_detail_warning);

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
                MyRequestTingEditData myRequestTingEditData = new MyRequestTingEditData();
                myRequestTingEditData.userId = userId;
                myRequestTingEditData.price = price;
                myRequestTingEditData.request = request;
                myRequestTingEditData.warning = warningEdit.getText().toString();

                Call<MyRequestTingEditResult> myRequestTingEditResultCall = service.getMyRequestTingEditResult(tingId,myRequestTingEditData);
                myRequestTingEditResultCall.enqueue(new Callback<MyRequestTingEditResult>() {
                    @Override
                    public void onResponse(Call<MyRequestTingEditResult> call, Response<MyRequestTingEditResult> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent();
                            intent.putExtra("request", request);
                            intent.putExtra("warning", warningEdit.getText().toString());
                            setResult(RESULT_OK, intent);
                            Toast.makeText(ChangeMyRequestDetail.this,"팅 수정이 완료되었습니다." , Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangeMyRequestDetail.this, "통신 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyRequestTingEditResult> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
