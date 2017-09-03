package sopt.client.cleanting.Mypage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import sopt.client.cleanting.R;

/**
 * Created by glgld on 2017-06-29.
 */

public class CustomDialog2 extends Dialog
{
    public EditText getEdit_phonenumber() {
        return edit_phonenumber;
    }

    private EditText edit_phonenumber;
    private ImageView aa,bb;
    private ImageView mLeftButton;
    private ImageView mRightButton;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog2);

        aa=(ImageView)findViewById(R.id.aa);
        bb=(ImageView)findViewById(R.id.bb);
        edit_phonenumber=(EditText)findViewById(R.id.edit_phonenumber);


        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"인증번호를 전송했습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"인증번호를 재전송했습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        mLeftButton = (ImageView) findViewById(R.id.btn_left);
        mRightButton = (ImageView) findViewById(R.id.btn_right);

        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialog2(Context context, String title,
                         View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public CustomDialog2(Context context,
                         View.OnClickListener leftListener,
                         View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}
