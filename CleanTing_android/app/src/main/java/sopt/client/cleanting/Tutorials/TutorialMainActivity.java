package sopt.client.cleanting.Tutorials;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import sopt.client.cleanting.R;

public class TutorialMainActivity extends AppCompatActivity {

    ViewPager vp;
//    public static Activity activity = new Activity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_main);

//        activity = this;

        vp = (ViewPager)findViewById(R.id.vp);
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
    }
    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new FirstTutorialFragment();
                case 1:
                    return new SecondTutorialFragment();
                case 2:
                    return new ThirdTutorialFragment();
                case 3:
                    return new FourthTutorialFragment();
                case 4:
                    return new FifthTutorialFragment();
                case 5:
                    return new SixthFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 6;
        }
    }
}
