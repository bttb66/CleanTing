package sopt.client.cleanting.Main.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.LoginUserDatas;
import sopt.client.cleanting.Main.MainActivity;
import sopt.client.cleanting.Main.SignUp.SignUpActivity;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;
import sopt.client.cleanting.Tutorials.TutorialMainActivity;

public class LoginActivity extends AppCompatActivity {

    ImageView Signup_img;
    ImageView Findpassword_img;
    ImageView Login_btn;
    EditText edit_id;
    EditText edit_password;
    CheckBox checkBox;
    NetworkService service;

    String loginId, loginPwd;
    boolean turorial = true;

    public static LoginUserDatas loginUserDatas = new LoginUserDatas();

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        service = ApplicationController.getInstance().getNetworkService();

        Signup_img = (ImageView) findViewById(R.id.Signup_img);
        Login_btn = (ImageView) findViewById(R.id.Login_btn);
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_password = (EditText) findViewById(R.id.edit_password);
        Findpassword_img = (ImageView) findViewById(R.id.Findpassword_tv);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        try {
            Bundle bundle = getIntent().getBundleExtra("logout");
            if (bundle.getString("logout").equals("0")) {
                SharedPreferences loginData = getSharedPreferences("loginData", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginData.edit();
                editor.clear();
                editor.commit();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences loginData = getSharedPreferences("loginData", Activity.MODE_PRIVATE);
        loginId = loginData.getString("id","");
        loginPwd = loginData.getString("pw","");
        if(!loginId.equals("") && !loginPwd.equals("")){
            SendLoginData sendLoginData = new SendLoginData();
            sendLoginData.userId = loginId;
            sendLoginData.pwd = loginPwd;
            String token = FirebaseInstanceId.getInstance().getToken();
            sendLoginData.deviceToken = token;
            Log.d("FCM_Token", token);
            Call<LoginResult> loginResultCall = service.getLoginResult(sendLoginData);
            loginResultCall.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if(response.isSuccessful()){
                        loginUserDatas.token = response.body().token;
                        loginUserDatas.userId = response.body().userInfo.userId;
                        loginUserDatas.name = response.body().userInfo.name;
                        loginUserDatas.phone = response.body().userInfo.phone;
                        loginUserDatas.address = response.body().userInfo.address;
                        loginUserDatas.lat = response.body().userInfo.lat;
                        loginUserDatas.lng = response.body().userInfo.lng;
                        loginUserDatas.locationNum = response.body().userInfo.locationNum;
                        loginUserDatas.push = response.body().userInfo.push;

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {

                }
            });
        } else {  // 튜토리얼 보이기
            if(turorial) {
                turorial = false;
                Intent intent = new Intent(LoginActivity.this, TutorialMainActivity.class);
                startActivity(intent);
            }
        }


        Findpassword_img.setOnClickListener(new View.OnClickListener() {// 계정찾기 페이지로 넘어 감
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(intent);
            }
        });


        Signup_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_id.getText().toString().replace(" ", "").equals("") || edit_password.getText().toString().replace(" ", "").equals("")) {
                    Toast.makeText(getApplicationContext(), " 아이디 혹은 비밀번호를 입력하세요 ", Toast.LENGTH_SHORT).show();
                    edit_id.setText("");
                    edit_password.setText("");
                } else {
                    SendLoginData sendLoginData = new SendLoginData();
                    sendLoginData.userId = edit_id.getText().toString();
                    sendLoginData.pwd = edit_password.getText().toString();
                    String token = FirebaseInstanceId.getInstance().getToken();
                    sendLoginData.deviceToken = token;
                    Log.d("FCM_Token", token);

                    Call<LoginResult> loginResultCall = service.getLoginResult(sendLoginData);
                    loginResultCall.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            if (response.isSuccessful()) {
                                if(checkBox.isChecked()){
                                    SharedPreferences loginData = getSharedPreferences("loginData",Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = loginData.edit();
                                    editor.putString("id", edit_id.getText().toString());
                                    editor.putString("pw", edit_password.getText().toString());
                                    editor.commit();
                                    Toast.makeText(LoginActivity.this, "자동로그인이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    loginUserDatas.token = response.body().token;
                                    loginUserDatas.userId = response.body().userInfo.userId;
                                    loginUserDatas.name = response.body().userInfo.name;
                                    loginUserDatas.phone = response.body().userInfo.phone;
                                    loginUserDatas.address = response.body().userInfo.address;
                                    loginUserDatas.lat = response.body().userInfo.lat;
                                    loginUserDatas.lng = response.body().userInfo.lng;
                                    loginUserDatas.locationNum = response.body().userInfo.locationNum;
                                    loginUserDatas.push = response.body().userInfo.push;
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, "아이디, 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "서버상태를 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
