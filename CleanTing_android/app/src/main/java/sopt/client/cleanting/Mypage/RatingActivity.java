package sopt.client.cleanting.Mypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Cleanner.AddCleanerReviewResult;
import sopt.client.cleanting.Cleanner.SearchCleanerDetailResult;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class RatingActivity extends AppCompatActivity {

    //이름 id값이랑 같게 씀!
    ImageView cleaner_picture;
    TextView Rate_cleanername;
    TextView Rate_cleannumber;
    TextView Rate_reviewnumber;
    TextView Rate_career;
    TextView Rate_age;
    RatingBar rating_bar;
    EditText inputCommentEdits;
    NetworkService service;

    ImageView H_star_rate1;
    ImageView H_star_rate2;
    ImageView H_star_rate3;
    ImageView H_star_rate4;
    ImageView H_star_rate5;

    String ratingnum = "0";

    ImageView Rate_complete_btn;
    EditText inputCommentEdit;
    SendAddCleanerReviewData sendAddCleanerData;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        service = ApplicationController.getInstance().getNetworkService();

        cleaner_picture=(ImageView)findViewById(R.id.cleaner_picture);
        Rate_cleanername=(TextView)findViewById(R.id.Rate_cleanername);
        Rate_reviewnumber=(TextView)findViewById(R.id.Rate_reviewnumber);
        Rate_career=(TextView)findViewById(R.id.Rate_career);
        Rate_age=(TextView)findViewById(R.id.Rate_age);

        inputCommentEdits=(EditText)findViewById(R.id.review_content_edtit);

        final String cleanerId =getIntent().getStringExtra("cleanerId");

        H_star_rate1 = (ImageView)findViewById(R.id.Rate_star1);
        H_star_rate2 = (ImageView)findViewById(R.id.Rate_star2);
        H_star_rate3 = (ImageView)findViewById(R.id.Rate_star3);
        H_star_rate4 = (ImageView)findViewById(R.id.Rate_star4);
        H_star_rate5 = (ImageView)findViewById(R.id.Rate_star5);
        Rate_complete_btn = (ImageView)findViewById(R.id.rate_complete_btn);

        H_star_rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H_star_rate1.setImageResource(R.drawable.star);
                H_star_rate2.setImageResource(R.drawable.star_line);
                H_star_rate3.setImageResource(R.drawable.star_line);
                H_star_rate4.setImageResource(R.drawable.star_line);
                H_star_rate5.setImageResource(R.drawable.star_line);

                ratingnum = "1";
            }
        });
        H_star_rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H_star_rate1.setImageResource(R.drawable.star);
                H_star_rate2.setImageResource(R.drawable.star);
                H_star_rate3.setImageResource(R.drawable.star_line);
                H_star_rate4.setImageResource(R.drawable.star_line);
                H_star_rate5.setImageResource(R.drawable.star_line);

                ratingnum = "2";
            }
        });
        H_star_rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H_star_rate1.setImageResource(R.drawable.star);
                H_star_rate2.setImageResource(R.drawable.star);
                H_star_rate3.setImageResource(R.drawable.star);
                H_star_rate4.setImageResource(R.drawable.star_line);
                H_star_rate5.setImageResource(R.drawable.star_line);

                ratingnum = "3";
            }
        });
        H_star_rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H_star_rate1.setImageResource(R.drawable.star);
                H_star_rate2.setImageResource(R.drawable.star);
                H_star_rate3.setImageResource(R.drawable.star);
                H_star_rate4.setImageResource(R.drawable.star);
                H_star_rate5.setImageResource(R.drawable.star_line);

                ratingnum = "4";
            }
        });
        H_star_rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H_star_rate1.setImageResource(R.drawable.star);
                H_star_rate2.setImageResource(R.drawable.star);
                H_star_rate3.setImageResource(R.drawable.star);
                H_star_rate4.setImageResource(R.drawable.star);
                H_star_rate5.setImageResource(R.drawable.star);

                ratingnum = "5";
            }
        });

        Call<SearchCleanerDetailResult> searchCleanerDetailResultCall5 = service.getSearchCleanerDetailResult(cleanerId);
        searchCleanerDetailResultCall5.enqueue(new Callback<SearchCleanerDetailResult>() {
            @Override
            public void onResponse(Call<SearchCleanerDetailResult> call, Response<SearchCleanerDetailResult> response) {
                if(response.isSuccessful())
                {
                    Glide.with(getApplicationContext()).load(response.body().result.cleaner.image).into(cleaner_picture);
                    Rate_cleanername.setText(response.body().result.cleaner.name + " 클리너");
                    Rate_reviewnumber.setText("리뷰 : "+response.body().result.cleaner.review_cnt +"건");
                    Rate_career.setText("경력 : "+response.body().result.cleaner.career +"개월");
                    Rate_age.setText("나이 : "+response.body().result.cleaner.age+ "세");
                }
            }

            @Override
            public void onFailure(Call<SearchCleanerDetailResult> call, Throwable t) {

            }
        });

        sendAddCleanerData = new SendAddCleanerReviewData();

        Rate_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAddCleanerData.content = inputCommentEdits.getText().toString();
                sendAddCleanerData.userId = loginUserDatas.userId;
                sendAddCleanerData.rating = ratingnum;
                Call<AddCleanerReviewResult> addCleanerReviewResultCall = service.getAddCleanerReviewResult(cleanerId,sendAddCleanerData);
                addCleanerReviewResultCall.enqueue(new Callback<AddCleanerReviewResult>() {
                    @Override
                    public void onResponse(Call<AddCleanerReviewResult> call, Response<AddCleanerReviewResult> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"리뷰 등록 성공",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<AddCleanerReviewResult> call, Throwable t) {

                    }
                });
            }
        });

    }
}
