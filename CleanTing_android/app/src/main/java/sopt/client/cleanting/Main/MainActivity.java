package sopt.client.cleanting.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import sopt.client.cleanting.Alarm.AlarmFragment;
import sopt.client.cleanting.Community.CommunityFragment;
import sopt.client.cleanting.MyRequest.MyRequestFragment;
import sopt.client.cleanting.Mypage.MypageFragment;
import sopt.client.cleanting.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static ArrayList<Activity> activityArrayList = new ArrayList<Activity>();

    public ImageView myRequestBtn, makeTingBtn, alarmBtn, mypageBtn;
    MyRequestFragment myRequestFragment;
    CommunityFragment communityFragment;
    AlarmFragment alarmFragment;
    MypageFragment mypageFragment;
    public LinearLayout linearLayoutTab;
    int previousBtn = 1;
    public static int REQUEST_JOIN = 1000;
    public static int REQUEST_MAKETING = 1001;
    public static int REQUEST_MODIFY_TING = 1002;
    public static int REQUEST_CANCEL_TING = 1003;
    public static int REQUEST_SELECT_CLEANER = 1004;

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(activityArrayList.size() == 0){
            activityArrayList.add(this);
        }

        myRequestBtn = (ImageView)findViewById(R.id.my_request_btn);
        makeTingBtn = (ImageView)findViewById(R.id.make_ting_btn);
        alarmBtn = (ImageView)findViewById(R.id.alarm_btn);
        mypageBtn = (ImageView)findViewById(R.id.mypage_btn);
        linearLayoutTab = (LinearLayout)findViewById(R.id.main_tab_layout);

        myRequestFragment = new MyRequestFragment();
        communityFragment = new CommunityFragment();
        alarmFragment = new AlarmFragment();
        mypageFragment = new MypageFragment();

        myRequestFragment.setContext(getApplicationContext());
        communityFragment.setContext(getApplicationContext());
        alarmFragment.setContext(getApplicationContext());
        mypageFragment.setContext(getApplicationContext());

        myRequestBtn.setOnClickListener(this);
        makeTingBtn.setOnClickListener(this);
        alarmBtn.setOnClickListener(this);
        mypageBtn.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, myRequestFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_request_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, myRequestFragment).commit();
                previousBtnImgSet();
                previousBtn = 1;
                myRequestBtn.setImageResource(R.drawable.home_icon_y);
                break;

            case R.id.make_ting_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, communityFragment).commit();
                previousBtnImgSet();
                previousBtn = 2;
                makeTingBtn.setImageResource(R.drawable.community_icon_y);
                break;

            case R.id.alarm_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, alarmFragment).commit();
                previousBtnImgSet();
                previousBtn = 3;
                alarmBtn.setImageResource(R.drawable.notice_icon_y);
                break;

            case R.id.mypage_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, mypageFragment).commit();
                previousBtnImgSet();
                previousBtn = 4;
                mypageBtn.setImageResource(R.drawable.mypage_icon_y);
                break;
        }
    }

    public void previousBtnImgSet(){
        switch (previousBtn){
            case 1:
                myRequestBtn.setImageResource(R.drawable.home_icon_g);
                break;

            case 2:
                makeTingBtn.setImageResource(R.drawable.community_icon_g);
                break;

            case 3:
                alarmBtn.setImageResource(R.drawable.notice_icon_g);
                break;

            case 4:
                mypageBtn.setImageResource(R.drawable.mypage_icon_g);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(getApplicationContext(),"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            this.finish();
            toast.cancel();
        }
    }
}
