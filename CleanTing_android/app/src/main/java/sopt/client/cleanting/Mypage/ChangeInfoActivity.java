package sopt.client.cleanting.Mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.SignUp.SearchAddressActivity;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class ChangeInfoActivity extends AppCompatActivity {

    private CustomDialog2 mCustomDialog2;
    ImageView changephone;
    ImageView button_pw,button_phonenumber,button_address;
    NetworkService service;
   EditText edittext_phonenumber;
    EditText password1,password2;
    EditText edit_phonenumber;
    TextView address_edit_tv;

    ImageView cab;

    String finalData;
    String data1;

    ModifyUserAddressData modifyUserAddressData;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        service = ApplicationController.getInstance().getNetworkService();


        button_pw = (ImageView) findViewById(R.id.button_pw);
        button_phonenumber=(ImageView)findViewById(R.id.button_phonenumber);
        button_address=(ImageView)findViewById(R.id.button_address);
        password1 = (EditText)findViewById(R.id.password1);
        password2 = (EditText)findViewById(R.id.password2);
        edittext_phonenumber=(EditText) findViewById(R.id.text_phonenumber);

        address_edit_tv = (TextView)findViewById(R.id.address_edit_tv);

        cab = (ImageView)findViewById(R.id.new_adress_search);

        cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangeInfoActivity.this, SearchAddressActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        button_pw.setOnClickListener(new View.OnClickListener() {       // 수정하기

            @Override
            public void onClick(View v) {
            if(Pattern.matches("^[a-zA-Z0-9]*$",password1.getText().toString())){
                if(password1.getText().toString().length()>=4 && password1.getText().toString().length()<=20){
                    if(password1.getText().toString().equals(password2.getText().toString())){
                    /*@Body로 바꾸고 수정해야함*/
                        Call<ModifyPasswordResult> modifyPasswordResultCall = service.getModifyPasswordResult(loginUserDatas.userId,password1.getText().toString());
                        modifyPasswordResultCall.enqueue(new Callback<ModifyPasswordResult>() {
                            @Override
                            public void onResponse(Call<ModifyPasswordResult> call, Response<ModifyPasswordResult> response) {
                                if (response.isSuccessful()){
                                    if(response.body().message.equals("pwd update ok")){
                                        Toast.makeText(ChangeInfoActivity.this, "비밀번호 변경에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ChangeInfoActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModifyPasswordResult> call, Throwable t) {
                                Toast.makeText(ChangeInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(ChangeInfoActivity.this, "비밀번호를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
                        password2.requestFocus();
                        return;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"패스워드를 4~20자리로 입력해주세요",Toast.LENGTH_SHORT).show();
                    password1.requestFocus();
                    return;
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"비밀번호를 영문과 숫자로만 입력해주세요.",Toast.LENGTH_SHORT).show();
                password1.requestFocus();
                return;
            }
//                if(password1.getText().toString().equals(password2.getText().toString())){
//                    /*@Body로 바꾸고 수정해야함*/
//                    Call<ModifyPasswordResult> modifyPasswordResultCall = service.getModifyPasswordResult(loginUserDatas.userId,password1.getText().toString());
//                    modifyPasswordResultCall.enqueue(new Callback<ModifyPasswordResult>() {
//                        @Override
//                        public void onResponse(Call<ModifyPasswordResult> call, Response<ModifyPasswordResult> response) {
//                            if (response.isSuccessful()){
//                                if(response.body().message.equals("pwd update ok")){
//                                    Toast.makeText(ChangeInfoActivity.this, "비밀번호 변경에 성공했습니다.", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(ChangeInfoActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ModifyPasswordResult> call, Throwable t) {
//                            Toast.makeText(ChangeInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText(ChangeInfoActivity.this, "비밀번호를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
//                    password2.requestFocus();
//                    return;
//                }
            }
        });

        button_phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ModifyUserPhoneResult> modifyUserPhoneResultCall =service.getModifyUserPhoneResult(loginUserDatas.userId,edittext_phonenumber.getText().toString());

                modifyUserPhoneResultCall.enqueue(new Callback<ModifyUserPhoneResult>() {
                    @Override
                    public void onResponse(Call<ModifyUserPhoneResult> call, Response<ModifyUserPhoneResult> response) {
                        if(response.isSuccessful()){
                            if(response.body().message.equals("phone update ok")){
                                Toast.makeText(getApplicationContext(),"휴대폰번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),response.body().message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModifyUserPhoneResult> call, Throwable t) {
                        Toast.makeText(ChangeInfoActivity.this, "서버 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        button_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modifyUserAddressData = new ModifyUserAddressData();
                modifyUserAddressData.address = data1;
                modifyUserAddressData.locationNum = loginUserDatas.locationNum;
                Call<ModifyUserAddressResult> modifyUserAddressResultCall = service.getModifyUserAddressResult(loginUserDatas.userId,modifyUserAddressData);
                modifyUserAddressResultCall.enqueue(new Callback<ModifyUserAddressResult>() {
                    @Override
                    public void onResponse(Call<ModifyUserAddressResult> call, Response<ModifyUserAddressResult> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().message.equals("address update ok")) {
                                Toast.makeText(getApplicationContext(), "주소 변경 완료", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext()," "+response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModifyUserAddressResult> call, Throwable t) {

                    }
                });
            }
        });

    }
//    private View.OnClickListener leftListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            mCustomDialog2.dismiss();
//        }
////    };

//    private View.OnClickListener rightListener = new View.OnClickListener() {
//
//        public void onClick(View v) {
//
//            text_phonenumber.setText(mCustomDialog2.getEdit_phonenumber().getText().toString());
//
////            Call<ModifyUserPhoneResult> modifyUserPhoneResultCall=service.getModifyUserPhoneResult(loginUserDatas.userId,edit_phonenumber.getText().toString());
////            modifyUserPhoneResultCall.enqueue(new Callback<ModifyUserPhoneResult>() {
////                @Override
////                public void onResponse(Call<ModifyUserPhoneResult> call, Response<ModifyUserPhoneResult> response) {
////                    if(response.isSuccessful()){
////                        if(response.body().message.equals("phone update ok")){
////                            Toast.makeText(getApplicationContext(), "phone update ok", Toast.LENGTH_SHORT).show();
////                        }
////                        else{
////                            Toast.makeText(getApplicationContext(),response.body().message,Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                }
////
////                @Override
////                public void onFailure(Call<ModifyUserPhoneResult> call, Throwable t) {
////                    Toast.makeText(getApplicationContext(),"서버 연결 상태를 확인해주세요.",Toast.LENGTH_SHORT).show();
////                }
////            });
//
//
//            mCustomDialog2.dismiss();
//
//        }
//    };
//
//    public void changeDialog(View view)
//    {
//        mCustomDialog2 = new CustomDialog2(this,
//                leftListener, // 왼쪽 버튼 이벤트
//                rightListener); // 오른쪽 버튼 이벤트
//        mCustomDialog2.show();
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case SEARCH_ADDRESS_ACTIVITY:

                if (resultCode == RESULT_OK) {

                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        StringTokenizer st1 = new StringTokenizer(data, ",");
                        st1.nextToken();
                        data1 = st1.nextToken();
                        StringTokenizer st2 = new StringTokenizer(data1, "(");
                        finalData = st2.nextToken();
                        address_edit_tv.setText(data1);
                    }
                }
                break;

        }
    }

}
