package sopt.client.cleanting.Main.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Mypage.ModifyPasswordResult;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

public class FindAccountActivity extends AppCompatActivity {

    NetworkService service;

    EditText edit_phonenumber,edit_citenumber,new_edit_phonenumber,new_edit_citenumber;
    EditText new_password,edit_confirm_password;
    ImageView button_request_citenumber,button_find,new_button_citenumber,new_button_find,button_new_password;
    ImageView recreate;

    TextView et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service= ApplicationController.getInstance().getNetworkService();

        setContentView(R.layout.activity_find_account);

        edit_phonenumber=(EditText)findViewById(R.id.edit_phonenumber);
        new_edit_phonenumber=(EditText)findViewById(R.id.new_edit_phonenumber);
        new_password=(EditText)findViewById(R.id.new_password);
        edit_confirm_password=(EditText)findViewById(R.id.edit_confirm_password);

        button_request_citenumber=(ImageView)findViewById(R.id.button_request_citenumber);
        new_button_citenumber=(ImageView)findViewById(R.id.new_button_citenumber);

        recreate=(ImageView)findViewById(R.id.button_new_password);

        et1 = (TextView)findViewById(R.id.et1);
        et2 = (TextView)findViewById(R.id.et2);
        et3 = (TextView)findViewById(R.id.et3);

        Typeface typeFace = Typeface.createFromAsset(getAssets(),"Helvetica_.ttf");
        et1.setTypeface(typeFace);
        et2.setTypeface(typeFace);
        et3.setTypeface(typeFace);

        //아이디찾기 인증번호 요청버튼
        button_request_citenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_SHORT).show();

            }
        });
        //아이디찾기 찾기버튼

        //새 비밀번호 인증번호 요청버튼(Toast메시지만)
        new_button_citenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
//        // 새 비밀번호 찾기버튼
//        new_button_find.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//            Call<FindPhoneNumberResult>findPhoneNumberResultCall = service.getFindPhoneNumberResult(new_edit_phonenumber.getText().toString());
//                findPhoneNumberResultCall.enqueue(new Callback<FindPhoneNumberResult>() {
//                    @Override
//                    public void onResponse(Call<FindPhoneNumberResult> call, Response<FindPhoneNumberResult> response) {
//                        if(response.isSuccessful()){
//                            if(response.body().message.equals("존재하는 회원입니다. 비밀번호를 변경해 주세요")){
//                                Toast.makeText(getApplicationContext(),"존재하는 회원입니다. 비밀번호를 변경해 주세요",Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(),response.body().message,Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<FindPhoneNumberResult> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),"서버상태를 확인해주세요",Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });

        recreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Call<ModifyPasswordResult> modifyPasswordResultCall=service.getModifyPasswordResult(new_password.getText().toString());
                    if(Pattern.matches("^[a-zA-Z0-9]*$",new_password.getText().toString())){
                        if(new_password.getText().toString().length()>=4 && new_password.getText().toString().length()<=20){

                            if(new_password==edit_confirm_password){
                                modifyPasswordResultCall.enqueue(new Callback<ModifyPasswordResult>() {
                                    @Override
                                    public void onResponse(Call<ModifyPasswordResult> call, Response<ModifyPasswordResult> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().message.equals("비밀번호 변경 성공")){
                                                Toast.makeText(getApplicationContext(),"비밀번호 변경 성공",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),response.body().message,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModifyPasswordResult> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"서버상태를 확인해주세요",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"비밀번호를 4~20자리로 입력해주세요",Toast.LENGTH_SHORT).show();
                            new_password.requestFocus();
                            return;
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"비밀번호를 영문과 숫자로만 입력해주세요",Toast.LENGTH_SHORT).show();
                        new_password.requestFocus();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(FindAccountActivity.this, "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
