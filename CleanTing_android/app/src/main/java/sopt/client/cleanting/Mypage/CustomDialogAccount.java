package sopt.client.cleanting.Mypage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-06-29.
 */

public class CustomDialogAccount extends Dialog
{
    private ImageView mRegister;
    private View.OnClickListener mRegisterClickListener;

    public CustomDialogAccount(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mRegisterClickListener = singleListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_custom_dialog_account);
        mRegister = (ImageView) findViewById(R.id.mRegister);


        // 클릭 이벤트 셋팅
        if (mRegisterClickListener != null) {
            mRegister.setOnClickListener(mRegisterClickListener);
        }
    }
}
