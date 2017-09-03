package sopt.client.cleanting.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import sopt.client.cleanting.Main.Login.LoginActivity;
import sopt.client.cleanting.R;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        // 트윈에니메이션 -Rotate 회전 xml의 코드를 로드해서 적용하기

        final ImageView iv = (ImageView) findViewById(R.id.water);
/* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
        Animation anim = AnimationUtils.loadAnimation(
                getApplicationContext(), // 현재 화면의 제어권자
                R.anim.rotate_anim);    // 설정한 에니메이션 파일
        iv.startAnimation(anim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
