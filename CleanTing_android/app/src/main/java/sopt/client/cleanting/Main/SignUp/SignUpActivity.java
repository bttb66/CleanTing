package sopt.client.cleanting.Main.SignUp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

public class SignUpActivity extends AppCompatActivity {


    ImageView before, button_cite, button_address;
    ImageView submit;
    EditText edit_name, edit_phonenumber, edit_citenumber, edit_password, edit_address, edit_id, edit_confirm_password;
    TextView agreement, information, approval, address1;
    TextView to_login;
    CheckBox checkbox;
    NetworkService service;
    ImageView doublecheck;
    String lat, lng, locationNum;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        service = ApplicationController.getInstance().getNetworkService();

        button_cite = (ImageView) findViewById(R.id.button_cite);
        button_address = (ImageView) findViewById(R.id.button_address);

        submit = (ImageView) findViewById(R.id.submit);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phonenumber = (EditText) findViewById(R.id.edit_phonenumber);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_confirm_password = (EditText) findViewById(R.id.edit_confirm_password);

        agreement = (TextView) findViewById(R.id.agreement);
        information = (TextView) findViewById(R.id.information);
        approval = (TextView) findViewById(R.id.approval);
        to_login = (TextView) findViewById(R.id.to_login);

        checkbox = (CheckBox) findViewById(R.id.checkbox);

        doublecheck = (ImageView) findViewById(R.id.doublecheck);

        address1 = (TextView) findViewById(R.id.address1);
        //중복확인
        doublecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<IdCheckResult> checkResultCall = service.getIdCheckResult(edit_id.getText().toString());
                checkResultCall.enqueue(new Callback<IdCheckResult>() {
                    @Override
                    public void onResponse(Call<IdCheckResult> call, Response<IdCheckResult> response) {
                        if (response.isSuccessful())
                        {
                            if (response.body().message.equals("사용가능한 아이디 입니다."))
                            {
                                Toast.makeText(getApplicationContext(),
                                        "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                if(response.body().message.equals("중복된 아이디입니다.")){
                                    Toast.makeText(getApplicationContext(),
                                            "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<IdCheckResult> call, Throwable t) {

                               Toast.makeText(getApplicationContext(),"서버상태를 확인해주세요",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        button_address.setOnClickListener(new View.OnClickListener() {  //주소 검색 버튼
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, SearchAddressActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //진영

                String name, phonenumber, password, confirm_password, address, id;
                String information;
                name = edit_name.getText().toString();
                phonenumber = edit_phonenumber.getText().toString();
                password = edit_password.getText().toString();
                address = address1.getText().toString();
                id = edit_id.getText().toString();
                confirm_password = edit_confirm_password.getText().toString();

                //유효성검사
                if (edit_name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_name.requestFocus();
                    return;
                }else if (edit_id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_id.requestFocus();
                    return;
                }else if (edit_phonenumber.length() == 0) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_phonenumber.requestFocus();
                    return;
                }else if (edit_password.length() < 4 && edit_password.length() > 20) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 4~20자리로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_password.requestFocus();
                    return;
//                }else if (edit_citenumber.length() == 0) {
//                    Toast.makeText(getApplicationContext(), "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
//                    return;
                }else if (address.equals("")) {
                    Toast.makeText(getApplicationContext(), "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_address.requestFocus();
                    return;
                }else if (!Pattern.matches("^[가-힣]*$", name)) {
                    Toast.makeText(getApplicationContext(), "이름을 한글로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!Pattern.matches("^[a-zA-Z0-9]*$$", id)) {
                    Toast.makeText(getApplicationContext(), "아이디를 영문과 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_name.requestFocus();
                    return;
                }else if (!Pattern.matches("^[a-zA-Z0-9]*$", password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 영문과 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    edit_password.requestFocus();
                    return;
                }else if (checkbox.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "위 약관에 동의해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!password.equals(confirm_password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SignUpData signUpData = new SignUpData();

                    signUpData.userId = edit_id.getText().toString();
                    signUpData.name = edit_name.getText().toString();
                    signUpData.phone = edit_phonenumber.getText().toString();
                    signUpData.address = address1.getText().toString() + " " + edit_address.getText().toString();
                    if (signUpData.address.contains("신림")) {
                        locationNum = "1";
                    } else if (signUpData.address.contains("역삼")) {
                        locationNum = "2";
                    } else {
                        locationNum = "3";
                    }
                    signUpData.locationNum = locationNum;
                    signUpData.pwd = edit_password.getText().toString();
                    signUpData.lat = lat;
                    signUpData.lng = lng;

                    Call<SignUpResult> signUpResultCall = service.getSignUpResult(signUpData);
                    signUpResultCall.enqueue(new Callback<SignUpResult>() {
                        @Override
                        public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입에 성공했습니다", Toast.LENGTH_LONG).show();
//                                    Intent intent=new Intent(getBaseContext(),LoginActivity.class);
//                                    startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        response.body().message, Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<SignUpResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "서버상태를 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
                startActivity(intent);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
            }
        });

    }

    public static Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case SEARCH_ADDRESS_ACTIVITY:

                if (resultCode == RESULT_OK) {

                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        StringTokenizer st1 = new StringTokenizer(data, ",");
                        st1.nextToken();
                        String data1 = st1.nextToken();
                        StringTokenizer st2 = new StringTokenizer(data1, "(");
                        String finalData = st2.nextToken();

                        address1.setText(data1);
                        Location location = findGeoPoint(SignUpActivity.this, finalData);
                        lat = String.valueOf(location.getLatitude());
                        lng = String.valueOf(location.getLongitude());
                        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
    }
}
