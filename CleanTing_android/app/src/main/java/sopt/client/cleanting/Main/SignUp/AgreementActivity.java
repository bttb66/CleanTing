package sopt.client.cleanting.Main.SignUp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.tsengvn.typekit.TypekitContextWrapper;

import sopt.client.cleanting.R;

public class AgreementActivity extends AppCompatActivity {
    ImageView button_back;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

//        button_back=(ImageView)findViewById(R.id.button_back);
//
//        button_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
