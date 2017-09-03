package sopt.client.cleanting.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.MainActivity;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static android.app.Activity.RESULT_OK;
import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

/**
 * Created by 김지원 on 2017-06-30.
 */

public class CommunityFragment extends Fragment {
    NetworkService service;
    Context context;

    //탭바 사라지기 위한 변수 -안성현
    boolean firstDragFlag = true;
    boolean dragFlag = false;   //현재 터치가 드래그 인지 확인
    float startYPosition = 0;
    float endYPosition;
    int selectPosition;
    boolean motionFlag;

    public ImageView more_btn;
    LinearLayout frameLinear;
    LinearLayout btn_remove;

    TextView search_tv;
    TextView filter_tv;
    ImageView Floatimg;

    TextView Bname;

    private RecyclerView BrecyclerView;
    private ArrayList<FindAllBulletinData> bulletinArrayList;
    private BulletinListRecylerAdapter BrecyclerAdapter;
    private LinearLayoutManager layoutManager;

    FindBulletinData Data = new FindBulletinData();
    BulletinPostData PostData = new BulletinPostData();
    ArrayList<BulletinCommentData> bulletinCommentDatas = new ArrayList<>();

    public static int REQUEST_COMMUNITY_WRITE = 2001;

    public CommunityFragment() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        service = ApplicationController.getInstance().getNetworkService();
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_community, container, false);

        more_btn = (ImageView) layout.findViewById(R.id.more_btn);
        frameLinear = (LinearLayout) layout.findViewById(R.id.frameLinear);

        Bname = (TextView)layout.findViewById(R.id.bulletin_name);

        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLinear.setVisibility(v.VISIBLE);
                more_btn.setVisibility(v.INVISIBLE);
            }
        });

        BrecyclerView = (RecyclerView) layout.findViewById(R.id.BulletinRecyclerView);
        BrecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        BrecyclerView.setLayoutManager(layoutManager);                           //리사이클러뷰에 레이아웃매니저를 달아준다

        bulletinArrayList = new ArrayList<FindAllBulletinData>();                         //사용자 정의 데이터를 갖는 arraylist

//        int locationnum = loginUserDatas.locationNum;
        BrecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:       //터치를 한 후 움직이고 있으면
                        dragFlag = true;
                        if(firstDragFlag) {     //터치후 계속 드래그 하고 있다면 ACTION_MOVE가 계속 일어날 것임으로 무브를 시작한 첫번째 터치만 값을 저장함
                            startYPosition = event.getY(); //첫번째 터치의 Y(높이)를 저장
                            firstDragFlag= false;   //두번째 MOVE가 실행되지 못하도록 플래그 변경
                        }

                        break;

                    case MotionEvent.ACTION_UP :
                        endYPosition = event.getY();

                        firstDragFlag= true;

                        if(dragFlag) {  //드래그를 하다가 터치를 실행
                            // 시작Y가 끝 Y보다 크다면 터치가 아래서 위로 이루어졌다는 것이고, 스크롤은 아래로내려갔다는 뜻이다.
                            // (startYPosition - endYPosition) > 10 은 터치로 이동한 거리가 10픽셀 이상은 이동해야 스크롤 이동으로 감지하겠다는 뜻임으로 필요하지 않으면 제거해도 된다.
                            if((startYPosition > endYPosition) && (startYPosition - endYPosition) > 10) {
                                //TODO 스크롤 다운 시 작업
                                ((MainActivity)getActivity()).linearLayoutTab.setVisibility(View.INVISIBLE);
                            }
                            //시작 Y가 끝 보다 작다면 터치가 위에서 아래로 이러우졌다는 것이고, 스크롤이 올라갔다는 뜻이다.
                            else if((startYPosition < endYPosition) && (endYPosition - startYPosition) > 10) {
                                //TODO 스크롤 업 시 작업
                                ((MainActivity)getActivity()).linearLayoutTab.setVisibility(View.VISIBLE);
                            }
                        }

                        startYPosition = 0.0f;
                        endYPosition = 0.0f;
                        motionFlag = false;
                        break;
                }
                return false;
            }
        });
        int locationnum = Integer.parseInt(loginUserDatas.locationNum);
        String bulletinname;
        if(locationnum == 1)
        {
            bulletinname = "신림동 게시판";
        }
        else if(locationnum == 2)
        {
            bulletinname = "역삼동 게시판";
        }
        else
        {
            bulletinname = "그 외 게시판";
        }

        Bname.setText(bulletinname);

        Call<FindAllBulletinResult> findAllBulletinResultCall = service.getFindAllBulletinResult(1);//locationnum);
        findAllBulletinResultCall.enqueue(new Callback<FindAllBulletinResult>() {
            @Override
            public void onResponse(Call<FindAllBulletinResult> call, Response<FindAllBulletinResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().message.equals("전체게시글 조회에 성공하였습니다")) {
                        bulletinArrayList = response.body().result;
                        BrecyclerAdapter = new BulletinListRecylerAdapter(bulletinArrayList, clickEvent);
                        BrecyclerView.setAdapter(BrecyclerAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "통신 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindAllBulletinResult> call, Throwable t) {

            }
        });

//        bulletinArrayList.add(new Bulletin("휴지 공동 구매 하실분", "2015.5.5 14:00","BC 마트에 휴지를 엄청 싸게 ~~~~","12"));
//        bulletinArrayList.add(new Bulletin("콜라 공동 구매 하실분", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));
//        bulletinArrayList.add(new Bulletin("제목", "날짜","사러가자","12"));

//        BrecyclerAdapter = new BulletinListRecylerAdapter(bulletinArrayList,clickEvent);
//        BrecyclerView.setAdapter(BrecyclerAdapter);

        btn_remove = (LinearLayout) layout.findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLinear.setVisibility(v.INVISIBLE);
                more_btn.setVisibility(v.VISIBLE);
            }
        });

        search_tv = (TextView) layout.findViewById(R.id.B_search);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunitySearchActivity.class);
                startActivity(intent);
            }
        });

        filter_tv = (TextView) layout.findViewById(R.id.B_filter);
        filter_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityFilterActivity.class);
                startActivity(intent);
            }
        });

        Floatimg = (ImageView) layout.findViewById(R.id.floatimg);
        Floatimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityWriteActivity.class);
                startActivityForResult(intent, REQUEST_COMMUNITY_WRITE);
            }
        });

        return layout;
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = BrecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함
            Call<FindBulletinResult> findbulletinresult = service.getFindBulletinResult(bulletinArrayList.get(itemPosition).postId);
            findbulletinresult.enqueue(new Callback<FindBulletinResult>() {
                @Override
                public void onResponse(Call<FindBulletinResult> call, Response<FindBulletinResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("게시물 상세조회에 성공하였습니다.")) {
                            Data = response.body().result;
                            PostData = Data.post;
                            bulletinCommentDatas = Data.comment;

                            Intent intent = new Intent(context, CommunityBulletinDetailActivity.class);
                            intent.putExtra("post", PostData);
                            intent.putExtra("comment", bulletinCommentDatas);
                            startActivity(intent);
//                            Toast.makeText(getContext(),"상세정보 성공 "+PostData.title,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "조회 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FindBulletinResult> call, Throwable t) {
                    Toast.makeText(getContext(), "통신 연결 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_COMMUNITY_WRITE) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this);
                ft.attach(this);
                ft.commit();
            }
        }
    }
}