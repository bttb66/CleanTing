package sopt.client.cleanting.Alarm;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;


public class AlarmFragment extends Fragment {
    private static final String[]  LIST_MENU ={"List1","List2","List3"} ;

    NetworkService service;
    Context context;
    ListView listview1,listview2,listview3;
    RelativeLayout groupA,groupB,groupC;
    LinearLayout groupA_item,groupB_item,groupC_item;
    TextView group1,group2,group3;
    AlarmAdapter Adapter1,Adapter2,Adapter3;
    static int flag_a=0;
    static int flag_b,flag_c=1;
    public AlarmFragment() {
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_alarm,container,false);
        service = ApplicationController.getInstance().getNetworkService();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        super.onCreate(savedInstanceState);

        ///////////////////////Parent랑 Child선언////////////////////////////
        groupA = (RelativeLayout)layout.findViewById(R.id.groupA);
        groupB = (RelativeLayout)layout.findViewById(R.id.groupB);
        groupC = (RelativeLayout)layout.findViewById(R.id.groupC);

        groupA_item=(LinearLayout)layout.findViewById(R.id.groupA_item);
        groupB_item=(LinearLayout)layout.findViewById(R.id.groupB_item);
        groupC_item=(LinearLayout)layout.findViewById(R.id.groupC_item);


        Adapter1 = new AlarmAdapter();
        Adapter2 = new AlarmAdapter();
        Adapter3 = new AlarmAdapter();

        listview1 = (ListView)layout.findViewById(R.id.listview1);
        listview2 = (ListView)layout.findViewById(R.id.listview2);
        listview3=(ListView)layout.findViewById(R.id.listview3);
//
        group1=(TextView)layout.findViewById(R.id.group1);
        group2=(TextView)layout.findViewById(R.id.group2);
        group3=(TextView)layout.findViewById(R.id.group3);

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"Helvetica_.ttf");
        group1.setTypeface(typeFace);
        group2.setTypeface(typeFace);
        group3.setTypeface(typeFace);

        Call<ReferAlarmResult> referAlarmResultCall=service.getReferAlarmResult(loginUserDatas.userId);
        referAlarmResultCall.enqueue(new Callback<ReferAlarmResult>() {
            @Override
            public void onResponse(Call<ReferAlarmResult> call, Response<ReferAlarmResult> response) {

                if(response.body().ret.size()!=0){
                    if(response.isSuccessful()){
                        if(response.body().message.equals("alarm query ok")){
                            ArrayList<ReferAlarmData> referAlarmDatas = response.body().ret;
                            ArrayList<ListData> listDatas1 = new ArrayList<ListData>();
                            ArrayList<ListData> listDatas2 = new ArrayList<ListData>();
                            ArrayList<ListData> listDatas3 = new ArrayList<ListData>();
                            int count = 0; //데이터가 변경이 없을경우
                            String tingId = referAlarmDatas.get(0).tingId;
                            for(int i=0;i<referAlarmDatas.size();i++){
                                if(!tingId.equals(referAlarmDatas.get(i).tingId)) count++;
                                tingId = referAlarmDatas.get(i).tingId;

                                if(count ==0){
                                    listDatas1.add(new ListData(referAlarmDatas.get(i).content,referAlarmDatas.get(i).time));
                                }else if(count == 1){
                                    listDatas2.add(new ListData(referAlarmDatas.get(i).content,referAlarmDatas.get(i).time));
                                }else{
                                    listDatas3.add(new ListData(referAlarmDatas.get(i).content,referAlarmDatas.get(i).time));
                                }

                            }
                            for(int i=0;i<listDatas1.size();i++){
                                Adapter1.addItem(listDatas1.get(i).content,listDatas1.get(i).time);
                                listview1.setAdapter(Adapter1);

                            }
                            for(int i=0;i<listDatas2.size();i++){
                                Adapter2.addItem(listDatas2.get(i).content,listDatas2.get(i).time);
                                groupB.setVisibility(View.VISIBLE);
                                listview2.setAdapter(Adapter2);
                            }
                            for(int i=0;i<listDatas3.size();i++){
                                Adapter3.addItem(listDatas3.get(i).content,listDatas3.get(i).time);
                                groupC.setVisibility(View.VISIBLE);
                                listview3.setAdapter(Adapter3);
                            }

                            //Adapter1.addItem();
/*                          Adapter2.addItem(response.body().ret.get(temp).content,response.body().ret.get(temp).time);

                            String [] index = new String[3];
                            index[0]=response.body().ret.get(0).tingId;
                            String tmp=index[0];

                            for(int i=0;i<response.body().ret.size();i++){
                               if(tmp!=response.body().ret.get(i).tingId){
                                   index[i]=response.body().ret.get(i).tingId;
                               }
                            }

                            for(int i=0;i<response.body().ret.size();i++){
                                int temp=i+1;
                                for(int j=i+1;j<response.body().ret.size();j++){
                                    if(index[i]!=index[j]){
                                        groupB.setVisibility(View.VISIBLE);
                                        Adapter2.addItem(response.body().ret.get(temp).content,response.body().ret.get(temp).time);
                                        listview2.setAdapter(Adapter2);
                                        i++;
                                        break;
                                    }
                                    else{
                                        temp++;
                                    }
                                }

                            }*/
//
//                            String flag = response.body().ret.get(0).tingId;//flag에 처음 받은 tingId 저장(groupA)
//                            String flag2 = response.body().ret.get(1).tingId;//groupB
//                            String flag3= response.body().ret.get(2).tingId;//groupC
//
//                            for(int i=0;i<response.body().ret.size();i++){
//                                if(response.body().ret.get(i).tingId.equals(flag)){
//                                    Adapter1.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                    listview1.setAdapter(Adapter1);
//                                }
//                                else if(flag!=flag2){
//                                    if(response.body().ret.get(i).tingId.equals(flag2)){
//                                        groupB.setVisibility(View.VISIBLE);
//                                        Adapter2.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                        listview2.setAdapter(Adapter2);
//                                    }//
//                                }
//                                else if(flag2!=flag3){
//                                         if(response.body().ret.get(i).tingId.equals(flag3)){
//                                        groupC.setVisibility(View.VISIBLE);
//                                        Adapter3.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                        listview3.setAdapter(Adapter3);
//                                    }//
//                                }
//
//                            }

                        }
                        else{
                            Toast.makeText(getContext(),response.body().message,Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getContext(),"알람이 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                }


//                if(response.isSuccessful()){
//                    if(response.body().message.equals("alarm query ok")){
//
//                        String flag = response.body().ret.get(0).tingId;//flag에 처음 받은 tingId 저장(groupA)
//                        String flag2 = response.body().ret.get(1).tingId;//groupB
//
//                        for(int i=0;i<response.body().ret.size();i++){
//                            if(response.body().ret.get(i).tingId.equals(flag)){
//                                Adapter1.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                listview1.setAdapter(Adapter1);
//                            }
//                            else if(response.body().ret.get(i).tingId.equals(flag2)){
//                                Adapter2.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                listview2.setAdapter(Adapter2);
//                            }
//                            else{
//                                Adapter3.addItem(response.body().ret.get(i).content,response.body().ret.get(i).time);
//                                listview3.setAdapter(Adapter3);
//                            }
//                        }
//
//                    }
//                    else{
//                        Toast.makeText(getContext(),response.body().message,Toast.LENGTH_SHORT).show();
//                    }
//                }
            }

            @Override
            public void onFailure(Call<ReferAlarmResult> call, Throwable t) {
                Toast.makeText(getContext(),"서버 연결 상태를 확인해주세요.",Toast.LENGTH_SHORT).show();

            }
        });




        ////////////////클릭하면 색깔바뀌게 하는 기능////////////////////////

        groupA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(flag_a ==0){
                    groupA_item.setVisibility(View.GONE);
                    groupA.setBackgroundResource(R.drawable.yellow_box_line);
                    flag_a=1;
                }
                else{
                    groupA_item.setVisibility(View.VISIBLE);
                    groupA.setBackgroundResource(R.drawable.yellow_box);
                    flag_a=0;
                }
            }
        });
        groupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_b==1){
                    groupB_item.setVisibility(View.VISIBLE);
                    groupB.setBackgroundResource(R.drawable.yellow_box);
                    flag_b=0;
                }
                else{
                    groupB_item.setVisibility(View.GONE);
                    groupB.setBackgroundResource(R.drawable.yellow_box_line);
                    flag_b=1;
                }
            }
        });
        groupC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_c==1){
                    groupC_item.setVisibility(View.VISIBLE);
                    groupC.setBackgroundResource(R.drawable.yellow_box);
                    flag_c=0;
                }
                else{
                    groupC_item.setVisibility(View.GONE);
                    groupC.setBackgroundResource(R.drawable.yellow_box_line);
                    flag_c=1;
                }
            }
        });
        ////////////////////////////////////////////////////////////////////

        return layout;
    }
    class ListData{
        public String content;
        public String time;

        public ListData(String content, String time) {
            this.content = content;
            this.time = time;
        }
    }


}


