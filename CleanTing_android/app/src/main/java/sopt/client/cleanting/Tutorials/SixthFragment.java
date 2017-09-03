package sopt.client.cleanting.Tutorials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sopt.client.cleanting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SixthFragment extends Fragment {

    ImageView img1;

    public SixthFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_sixth, container, false);

        img1 = (ImageView)layout.findViewById(R.id.skip_img);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
//                activity.finish();
                getActivity().finish();
            }
        });

        return layout;
    }

}
