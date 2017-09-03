package sopt.client.cleanting.Mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Alarm.AlarmOnOffResult;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.Login.LoginActivity;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;
import static sopt.client.cleanting.Main.MainActivity.activityArrayList;

/**
 * Created by 김지원 on 2017-06-25.
 */

public class MypageFragment extends Fragment{

    NetworkService service;

    Context context;
    ImageView Logout_btn;
    Switch alarm_switch;
    TextView leave_tv;
    TextView myhistory;
    TextView changeinfo;
    TextView adminaccount;

    private CustomDialogAccount mCustomDialogAcount;

    public MypageFragment() {
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        service = ApplicationController.getInstance().getNetworkService();
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_mypage,container,false);

        Logout_btn = (ImageView)layout.findViewById(R.id.Logout_btn);
        alarm_switch = (Switch)layout.findViewById(R.id.alarm_switch);
        leave_tv = (TextView)layout.findViewById(R.id.leave);
        myhistory = (TextView) layout.findViewById(R.id.myhistory);
        changeinfo = (TextView)layout.findViewById(R.id.changeinfo);
        adminaccount = (TextView)layout.findViewById(R.id.adminaccount);

        adminaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialogAcount = new CustomDialogAccount(getContext(),
                        registListener);
                mCustomDialogAcount.show();
            }
        });


        myhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MyhistoryActivity.class);
                startActivity(intent);
            }
        });

        leave_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LeaveActivity.class);
                startActivity(intent);
            }
        });
//        leave_tv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN){
//                    if(leave_tv.getClass()==v.getClass()){
//                        leave_tv.setTextColor(Color.WHITE);
//                    }
//                }
//                return false;
//            }
//        });

        changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ChangeInfoActivity.class);
                startActivity(intent);
            }
        });


        Logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"로그아웃",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("logout","0");
                i.putExtra("logout",bundle);
                startActivity(i);
                activityArrayList.get(0).finish();
                activityArrayList.clear();
            }
        });

        alarm_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AlarmOnOffResult> alarmOnOffResultCall=service.getAlarmOnOffResult(loginUserDatas.userId);

                if(alarm_switch.isChecked())
                {
                    alarmOnOffResultCall.enqueue(new Callback<AlarmOnOffResult>() {
                        @Override
                        public void onResponse(Call<AlarmOnOffResult> call, Response<AlarmOnOffResult> response) {
                            if(response.isSuccessful()){
                                if(response.message().equals("alarm query ok")){

                                    Toast.makeText(getContext(),"알림이 켜집니다",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(), response.body().message,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AlarmOnOffResult> call, Throwable t) {
                            Toast.makeText(getContext(),
                                    "서버상태를 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                    });


                }
                else
                {
                    Toast.makeText(getContext(),"알림이 꺼집니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        //   서버에서 정보 가져와서 알림이 켜져있으면 alarm_switch.setChecked(true);
        //   아니면 alarm_switch.setChecked(false);

        return layout;
    }

    private View.OnClickListener registListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(),"정상적으로 등록.",
                    Toast.LENGTH_SHORT).show();
            mCustomDialogAcount.dismiss();
        }
    };


}
