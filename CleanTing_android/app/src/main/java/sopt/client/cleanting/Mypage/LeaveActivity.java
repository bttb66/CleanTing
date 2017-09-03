package sopt.client.cleanting.Mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.Login.LoginActivity;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;
import static sopt.client.cleanting.Main.MainActivity.activityArrayList;

public class LeaveActivity extends AppCompatActivity {

    ImageView leave_btn;
    private CustomDialog mCustomDialog;
    NetworkService service;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        if(activityArrayList.size() == 1){
            activityArrayList.add(this);
        }

        leave_btn = (ImageView)findViewById(R.id.leave_btn);
        service= ApplicationController.getInstance().getNetworkService();


    }

    private View.OnClickListener leftListener = new View.OnClickListener() {//취소버튼
        public void onClick(View v) {
            mCustomDialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {//탈퇴버튼
        public void onClick(View v) {
            Call<WithdrawResult> withdrawResultCall = service.getWithdrawResult(loginUserDatas.userId);//loginUserDatas.userId
            withdrawResultCall.enqueue(new Callback<WithdrawResult>() {
                @Override
                public void onResponse(Call<WithdrawResult> call, Response<WithdrawResult> response) {
                    if(response.isSuccessful()){
                        if(response.body().message.equals("user withdraw success")){

                            Toast.makeText(getApplicationContext(),"정상적으로 탈퇴 되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                            mCustomDialog.dismiss();
                            SharedPreferences loginData = getSharedPreferences("loginData", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginData.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(LeaveActivity.this, LoginActivity.class);
                            for(int i=0; i<activityArrayList.size(); i++){
                                activityArrayList.get(i).finish();
                            }
                            activityArrayList.clear();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),response.body().message,Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WithdrawResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "서버상태를 확인해주세요", Toast.LENGTH_LONG).show();
                }
            });

        }
    };

    public void onclick_leave(View view)
    {
        mCustomDialog = new CustomDialog(this,
                " ", // 제목
                "정말로 탈퇴를 하시겠습니까?", // 내용
                leftListener, // 왼쪽 버튼 이벤트
                rightListener); // 오른쪽 버튼 이벤트
        mCustomDialog.show();
    }
}
