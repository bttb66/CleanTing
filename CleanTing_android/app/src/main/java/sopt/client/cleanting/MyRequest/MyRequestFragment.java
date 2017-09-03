package sopt.client.cleanting.MyRequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Main.MainActivity;
import sopt.client.cleanting.MakeTing.MakeTingLocationResult;
import sopt.client.cleanting.MakeTing.MakeTingLocationResultData;
import sopt.client.cleanting.MakeTing.SendTingLocationData;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static android.app.Activity.RESULT_OK;
import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;
import static sopt.client.cleanting.Main.MainActivity.REQUEST_JOIN;

/**
 * Created by 김지원 on 2017-06-25.
 */

public class MyRequestFragment extends Fragment{
    NetworkService service;
//    Bundle bundle;
    ArrayList<Bundle> bundleList = new ArrayList<Bundle>();
    Context context;
    RecyclerView recyclerMyLocation;
    ArrayList<MakeTingLocationResultData> itemData;
    LinearLayoutManager layoutManager;
    MyRequestAdapter myRequestAdapter;
    //탭바 사라지기 위한 변수 -안성현
    boolean firstDragFlag = true;
    boolean dragFlag = false;   //현재 터치가 드래그 인지 확인
    float startYPosition = 0;
    float endYPosition;
    int selectPosition;
    boolean motionFlag;
    ////////////////////////////////
    public static boolean refreshView = false;

    public MyRequestFragment() {
    }
    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        service = ApplicationController.getInstance().getNetworkService();
        final LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_myrequest,container,false);
        recyclerMyLocation = (RecyclerView)layout.findViewById(R.id.recycler_my_location);
        recyclerMyLocation.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMyLocation.setLayoutManager(layoutManager);


        itemData = new ArrayList<MakeTingLocationResultData>();
        SendTingLocationData sendTingLocationData = new SendTingLocationData();
        sendTingLocationData.userLat = loginUserDatas.lat;
        sendTingLocationData.userLng = loginUserDatas.lng;
        Call<MakeTingLocationResult> makeTingLocationResultCall = service.getMakeTingLocationResult(loginUserDatas.userId,sendTingLocationData);
        makeTingLocationResultCall.enqueue(new Callback<MakeTingLocationResult>() {
            @Override
            public void onResponse(Call<MakeTingLocationResult> call, Response<MakeTingLocationResult> response) {
                if(response.isSuccessful()){
                    itemData = response.body().result;
                    Call<RequestTingDetailResult> requestTingDetailResultCall = service.getRequestTingDetailResult(loginUserDatas.userId);
                    requestTingDetailResultCall.enqueue(new Callback<RequestTingDetailResult>() {
                        @Override
                        public void onResponse(Call<RequestTingDetailResult> call, Response<RequestTingDetailResult> response) {
                            if(response.isSuccessful()){
                                bundleList.clear();
                                for(int i=0; i<response.body().result.size(); i++){
                                    bundleList.add(getBundle(response.body().result.get(i)));
                                }
                                FragmentManager fm = getFragmentManager();
                                myRequestAdapter = new MyRequestAdapter(itemData,clickListener,context, fm, bundleList);
                                recyclerMyLocation.setAdapter(myRequestAdapter);
                                recyclerMyLocation.scrollToPosition(0);
                            }else {
                                Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<RequestTingDetailResult> call, Throwable t) {
                            Toast.makeText(context, "인터넷확인", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<MakeTingLocationResult> call, Throwable t) {

            }
        });

        //탭바 사라지기 위한  - 안성현
        recyclerMyLocation.setOnTouchListener(new View.OnTouchListener() {
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

//        if(refreshView){
//            refreshView = false;
////            Toast.makeText(context, "메인페이지 refresh", Toast.LENGTH_SHORT).show();
//            RefreshView();
//        }

        Log.d("MyRequestFragment", "onCreateView 호출");
        return layout;
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int temp_position = recyclerMyLocation.getChildPosition(view);
            if(temp_position == 0){
//                Toast.makeText(context, "헤더입니다.", Toast.LENGTH_SHORT).show();
            } else {
//                String temp = myRequestAdapter.itemDatas.get(temp_position-1).writer;
                MakeTingLocationResultData datas = itemData.get(temp_position-1);
                Intent intent = new Intent(context, MyRequestRecruit.class);
                intent.putExtra("datas", datas);
                selectPosition = temp_position-1;
                startActivityForResult(intent,REQUEST_JOIN);
            }
        }
    };
    public ArrayList<Bundle> getBundleList(){
        Call<RequestTingDetailResult> requestTingDetailResultCall = service.getRequestTingDetailResult(loginUserDatas.userId);
        requestTingDetailResultCall.enqueue(new Callback<RequestTingDetailResult>() {
            @Override
            public void onResponse(Call<RequestTingDetailResult> call, Response<RequestTingDetailResult> response) {
                if(response.isSuccessful()){
                    for(int i=0; i<response.body().result.size(); i++){
                        bundleList.add(getBundle(response.body().result.get(i)));
                    }
                    FragmentManager fm = getFragmentManager();
                    myRequestAdapter = new MyRequestAdapter(itemData,clickListener,context, fm, bundleList);
                    recyclerMyLocation.setAdapter(myRequestAdapter);
                    recyclerMyLocation.scrollToPosition(0);

                }else {
                    Toast.makeText(context, "통신 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestTingDetailResult> call, Throwable t) {
                Toast.makeText(context, "인터넷확인", Toast.LENGTH_SHORT).show();
            }
        });

        return bundleList;
    }

    public Bundle getBundle(RequestTingDetailResultData requestTingDetailResultData){
        Bundle bundle = new Bundle();
//        bundle.putString("img_url", img_url);
        bundle.putString("tingId", requestTingDetailResultData.tingId);
        bundle.putString("date", requestTingDetailResultData.date);
        bundle.putString("startTime", requestTingDetailResultData.startTime);
        bundle.putString("endTime", requestTingDetailResultData.endTime);
        bundle.putString("cnt", requestTingDetailResultData.cnt);
        bundle.putString("cleanerId", requestTingDetailResultData.cleanerId);
        bundle.putString("userId", requestTingDetailResultData.userId);
        bundle.putString("price", requestTingDetailResultData.price);
        bundle.putString("request", requestTingDetailResultData.request);
        bundle.putString("warning", requestTingDetailResultData.warning);
        bundle.putString("name", requestTingDetailResultData.name);
        bundle.putString("phone", requestTingDetailResultData.phone);
        bundle.putString("cleanerImg", requestTingDetailResultData.image);
        return bundle;
    }

    public void RefreshView(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(MyRequestFragment.this);
        ft.attach(MyRequestFragment.this);
        ft.commit();
//        Toast.makeText(context, "view 새로고침", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_JOIN) {
                Toast.makeText(context, "참여 완료", Toast.LENGTH_SHORT).show();
                RefreshView();
            }
//            RefreshView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyRequestFragment", "onResume 호출");
        if(refreshView){
            refreshView = false;
            RefreshView();
        }
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this);
//        ft.attach(this);
//        ft.commit();
//        if(refreshView){
//            RefreshView();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyRequestFragment", "onPause 호출 , ");
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(MyRequestFragment.this);
//        ft.commit();
//        refreshView = true;
//        refreshView = true;
    }
}