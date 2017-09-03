package sopt.client.cleanting.MakeTing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sopt.client.cleanting.Application.ApplicationController;
import sopt.client.cleanting.Cleanner.CleanerDateData;
import sopt.client.cleanting.Cleanner.CleanerReviewData;
import sopt.client.cleanting.Cleanner.SearchCleanerData;
import sopt.client.cleanting.Cleanner.SearchCleanerResult;
import sopt.client.cleanting.Cleanner.SearchLocationCleanerData;
import sopt.client.cleanting.Cleanner.SearchLocationCleanerResult;
import sopt.client.cleanting.Cleanner.SendSearchLocationCleanerData;
import sopt.client.cleanting.MakeTing.CleanerDetail.DetailCleanerActivity;
import sopt.client.cleanting.MakeTing.ListCleaner.ListCleanerData;
import sopt.client.cleanting.MakeTing.ListCleaner.ListCleanerRecyclerAdapter;
import sopt.client.cleanting.MakeTing.RecentCleaner.RecentCleanerDataArray;
import sopt.client.cleanting.MakeTing.RecentCleaner.RecentCleanerResult;
import sopt.client.cleanting.MakeTing.RecentCleaner.RecentRecyclerAdapter;
import sopt.client.cleanting.MakeTing.SearchList.SearchRecyclerAdapter;
import sopt.client.cleanting.Network.NetworkService;
import sopt.client.cleanting.R;

import static sopt.client.cleanting.Main.Login.LoginActivity.loginUserDatas;

public class ChooseCleanerActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    TextView SmallAdress;
    TextView ListTitle;

    private RecyclerView RrecyclerView;
    private ArrayList<RecentCleanerDataArray> Rcleaners;
    private RecentRecyclerAdapter RrecyclerAdapter;
    private LinearLayoutManager layoutManager;

    private RecyclerView LrecyclerView;
    private ArrayList<SearchLocationCleanerData> Lcleaners;
    private ListCleanerRecyclerAdapter ListRrecyclerAdapter;
    private LinearLayoutManager layoutManager2;

    private SearchRecyclerAdapter ListRecyclerAdapter2;

    private RecyclerView SearchrecyclerView;
    private ArrayList<ListCleanerData> Scleaners;
    private LinearLayoutManager layoutManager3;
    private ListCleanerRecyclerAdapter SearchrecyclerAdapter;

    EditText search;
    LinearLayout wrapLinear;
    LinearLayout Search_Linear;

    Spinner spinner;

    SearchCleanerData searchCleanerData = new SearchCleanerData();
    ArrayList<SearchCleanerData> searchCleanerDataArrayList;
    ArrayList<SearchCleanerData> searchCleanerDataArrayList1;
    ArrayList<SearchCleanerData> searchCleanerDataArrayList2;
    ArrayList<SearchCleanerData> searchCleanerDataArrayList3;

    SendSearchLocationCleanerData sendSearchLocationCleanerData;

    ArrayList<CleanerReviewData> reviewDatas;

    NetworkService service;

    ImageView c_search_img;

    @Override    //  글씨체 적용
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cleaner);

        final String inputdate = getIntent().getStringExtra("date");
        service = ApplicationController.getInstance().getNetworkService();

        SmallAdress = (TextView) findViewById(R.id.small_address);
        if(loginUserDatas.locationNum.equals("1"))
        {
            SmallAdress.setText("신림동");
        }
        if(loginUserDatas.locationNum.equals("2"))
        {
            SmallAdress.setText("역삼동");
        }
        if(loginUserDatas.locationNum.equals("3"))
        {
            SmallAdress.setText("그 외 지역");
        }
        ListTitle = (TextView) findViewById(R.id.listtitle);


        search = (EditText) findViewById(R.id.search_edit);
        wrapLinear = (LinearLayout) findViewById(R.id.wrapLinear);
        Search_Linear = (LinearLayout) findViewById(R.id.Search_Linear);

        c_search_img = (ImageView) findViewById(R.id.C_search_img);


        search.setOnEditorActionListener(this);
        search.addTextChangedListener(textWatcherInput);

        RrecyclerView = (RecyclerView) findViewById(R.id.Recent_recyclerview);
        RrecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);             //리니어레이아웃의 형태이면 방향은 수직
        RrecyclerView.setLayoutManager(layoutManager);                           //리사이클러뷰에 레이아웃매니저를 달아준다

        Rcleaners = new ArrayList<RecentCleanerDataArray>();                         //사용자 정의 데이터를 갖는 arraylist
        searchCleanerDataArrayList1 = new ArrayList<SearchCleanerData>();
        CleanerDateData cleanerDateData = new CleanerDateData();
        cleanerDateData.date = inputdate;

        Call<RecentCleanerResult> recentCleanerResultCall = service.getRecentCleanerResultNew(loginUserDatas.userId, cleanerDateData);
        recentCleanerResultCall.enqueue(new Callback<RecentCleanerResult>() {
            @Override
            public void onResponse(Call<RecentCleanerResult> call, Response<RecentCleanerResult> response) {
                if (response.isSuccessful()) {
                    Rcleaners = response.body().result;
                    RrecyclerAdapter = new RecentRecyclerAdapter(getApplicationContext(), Rcleaners, clickEvent);
                    RrecyclerView.setAdapter(RrecyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<RecentCleanerResult> call, Throwable t) {

            }
        });


        LrecyclerView = (RecyclerView) findViewById(R.id.Cleanerlist_recyclerview);
        LrecyclerView.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        LrecyclerView.setLayoutManager(layoutManager2);                           //리사이클러뷰에 레이아웃매니저를 달아준다
        Lcleaners = new ArrayList<SearchLocationCleanerData>();                         //사용자 정의 데이터를 갖는 arraylist
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        arrayList.add("별점순");
        arrayList.add("이력순");
        arrayList.add("리뷰순");
        ArrayAdapter adtRegion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner = (Spinner) findViewById(R.id.sort_spinner);
        spinner.setAdapter(adtRegion);

        sendSearchLocationCleanerData = new SendSearchLocationCleanerData();
        sendSearchLocationCleanerData.userId = loginUserDatas.userId;
        sendSearchLocationCleanerData.userLat = loginUserDatas.lat;
        sendSearchLocationCleanerData.userLng = loginUserDatas.lng;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sendSearchLocationCleanerData.order = "1";
                        ListTitle.setText("별점순 클리너");
                        // date의 형식???
                        Call<SearchLocationCleanerResult> searchLocationCleanerResult = service.getSearchLocationCleanerResult(inputdate, sendSearchLocationCleanerData);
                        searchLocationCleanerResult.enqueue(new Callback<SearchLocationCleanerResult>() {
                            @Override
                            public void onResponse(Call<SearchLocationCleanerResult> call, Response<SearchLocationCleanerResult> response) {
                                if (response.isSuccessful())
                                {
                                    Lcleaners = response.body().result;
                                    ListRecyclerAdapter2 = new SearchRecyclerAdapter(getApplicationContext(), Lcleaners, clickEvent2);
                                    LrecyclerView.setAdapter(ListRecyclerAdapter2);
                                    ListRecyclerAdapter2.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<SearchLocationCleanerResult> call, Throwable t) {

                            }
                        });
                        break;
                    case 1:
                        sendSearchLocationCleanerData.order = "2";
                        ListTitle.setText("이력순 클리너");
                        // date의 형식???
                        Call<SearchLocationCleanerResult> searchLocationCleanerResult2 = service.getSearchLocationCleanerResult(inputdate, sendSearchLocationCleanerData);
                        searchLocationCleanerResult2.enqueue(new Callback<SearchLocationCleanerResult>() {
                            @Override
                            public void onResponse(Call<SearchLocationCleanerResult> call, Response<SearchLocationCleanerResult> response) {
                                if (response.isSuccessful()) {
                                    Lcleaners = response.body().result;
                                    ListRecyclerAdapter2 = new SearchRecyclerAdapter(getApplicationContext(), Lcleaners, clickEvent2);
                                    LrecyclerView.setAdapter(ListRecyclerAdapter2);
                                    ListRecyclerAdapter2.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<SearchLocationCleanerResult> call, Throwable t) {

                            }
                        });
                        break;
                    case 2:
                        sendSearchLocationCleanerData.order = "3";
                        ListTitle.setText("리뷰순 클리너");
                        // date의 형식???
                        Call<SearchLocationCleanerResult> searchLocationCleanerResult3 = service.getSearchLocationCleanerResult(inputdate, sendSearchLocationCleanerData);
                        searchLocationCleanerResult3.enqueue(new Callback<SearchLocationCleanerResult>() {
                            @Override
                            public void onResponse(Call<SearchLocationCleanerResult> call, Response<SearchLocationCleanerResult> response) {
                                if (response.isSuccessful()) {
                                    Lcleaners = response.body().result;
                                    ListRecyclerAdapter2 = new SearchRecyclerAdapter(getApplicationContext(), Lcleaners, clickEvent2);
                                    LrecyclerView.setAdapter(ListRecyclerAdapter2);
                                    ListRecyclerAdapter2.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<SearchLocationCleanerResult> call, Throwable t) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sendSearchLocationCleanerData.order = "1";
                // date의 형식???
//                Call<SearchLocationCleanerResult> searchLocationCleanerResult = service.getSearchLocationCleanerResult(date,sendSearchLocationCleanerData);
//                searchLocationCleanerResult.enqueue(new Callback<SearchLocationCleanerResult>() {
//                    @Override
//                    public void onResponse(Call<SearchLocationCleanerResult> call, Response<SearchLocationCleanerResult> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<SearchLocationCleanerResult> call, Throwable t) {
//                    }
//                });
            }
        });


        SearchrecyclerView = (RecyclerView) findViewById(R.id.Search_recyclerview);
        SearchrecyclerView.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(this);

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = RrecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함

            Intent intent = new Intent(getApplicationContext(), DetailCleanerActivity.class);
            intent.putExtra("cleanerid", Rcleaners.get(itemPosition).cleanerId);
            startActivityForResult(intent,5005);

//            Toast.makeText(getApplicationContext(), itemPosition + "번 리스트 클릭!!", Toast.LENGTH_SHORT).show();
        }
    };

    public View.OnClickListener clickEvent2 = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = RrecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함

            Intent intent = new Intent(getApplicationContext(), DetailCleanerActivity.class);
            intent.putExtra("cleanerid", Lcleaners.get(itemPosition).cleanerId);
            startActivityForResult(intent,5005);

//            Toast.makeText(getApplicationContext(), itemPosition + "번 리스트 클릭!!", Toast.LENGTH_SHORT).show();
        }
    };
    public View.OnClickListener clickEvent3 = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = RrecyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함

            Intent intent = new Intent(getApplicationContext(), DetailCleanerActivity.class);
            intent.putExtra("cleanerid", searchCleanerDataArrayList1.get(itemPosition).cleanerId);
            startActivityForResult(intent,5005);

//            Toast.makeText(getApplicationContext(), itemPosition + "번 리스트 클릭!!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (v.getId()) {
            case R.id.search_edit: {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                }
                break;
            }
        }

        return false;
    }

    TextWatcher textWatcherInput = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            wrapLinear.setVisibility(View.INVISIBLE);
            Search_Linear.setVisibility(View.VISIBLE);
            final String str = s.toString();

            c_search_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SearchCleanerResult> searchCleanerResultCall = service.getSearchCleanerResult(str);
                    searchCleanerResultCall.enqueue(new Callback<SearchCleanerResult>() {
                        @Override
                        public void onResponse(Call<SearchCleanerResult> call, Response<SearchCleanerResult> response) {
                            if (response.isSuccessful()) {
                                searchCleanerDataArrayList1 = response.body().result;
                                layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
                                SearchrecyclerView.setLayoutManager(layoutManager3);
                                SearchrecyclerAdapter = new ListCleanerRecyclerAdapter(getApplicationContext(), searchCleanerDataArrayList1, clickEvent3);
                                SearchrecyclerView.setAdapter(SearchrecyclerAdapter);
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<SearchCleanerResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"통신 실패",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            if (s.toString().equals("")) {
                Search_Linear.setVisibility(View.GONE);
                wrapLinear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            wrapLinear.setVisibility(View.INVISIBLE);
            Search_Linear.setVisibility(View.VISIBLE);

            String str = s.toString();
//            ArrayList<ListCleanerData> arrayList;
//            arrayList = new ArrayList<>();
//            ListCleanerData listCleaner;
//            listCleaner = new ListCleanerData(str,"10","20");
//            arrayList.add(listCleaner);

//            Call<SearchCleanerResult> searchCleanerResultCall  = service.getSearchCleanerResult(str);
//            searchCleanerResultCall.enqueue(new Callback<SearchCleanerResult>() {
//                @Override
//                public void onResponse(Call<SearchCleanerResult> call, Response<SearchCleanerResult> response) {
//                    if(response.isSuccessful())
//                    {
//                        searchCleanerDataArrayList2 = new ArrayList<SearchCleanerData>();
//                        searchCleanerDataArrayList2 = response.body().result;
//                    }
//                    else
//                    {
//
//                    }
//                }
//                @Override
//                public void onFailure(Call<SearchCleanerResult> call, Throwable t) {
//
//                }
//            });

            layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
            SearchrecyclerView.setLayoutManager(layoutManager3);
            SearchrecyclerAdapter = new ListCleanerRecyclerAdapter(getApplicationContext(), searchCleanerDataArrayList2, clickEvent3);
            SearchrecyclerView.setAdapter(SearchrecyclerAdapter);

            if (s.toString().equals("")) {
                Search_Linear.setVisibility(View.GONE);
                wrapLinear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            wrapLinear.setVisibility(View.INVISIBLE);
            Search_Linear.setVisibility(View.VISIBLE);

            String str = s.toString();
//            ArrayList<ListCleanerData> arrayList;
//            arrayList = new ArrayList<>();
//            ListCleanerData listCleaner;
//            listCleaner = new ListCleanerData(str,"10","20");
//            arrayList.add(listCleaner);

//            Call<SearchCleanerResult> searchCleanerResultCall  = service.getSearchCleanerResult(str);
//            searchCleanerResultCall.enqueue(new Callback<SearchCleanerResult>() {
//                @Override
//                public void onResponse(Call<SearchCleanerResult> call, Response<SearchCleanerResult> response) {
//                    if(response.isSuccessful())
//                    {
//                        searchCleanerDataArrayList3 = new ArrayList<SearchCleanerData>();
//                        searchCleanerDataArrayList3 = response.body().result;
//                    }
//                    else
//                    {
//
//                    }
//                }
//                @Override
//                public void onFailure(Call<SearchCleanerResult> call, Throwable t) {
//
//                }
//            });
            layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
            SearchrecyclerView.setLayoutManager(layoutManager3);
            SearchrecyclerAdapter = new ListCleanerRecyclerAdapter(getApplicationContext(), searchCleanerDataArrayList3, clickEvent3);
            SearchrecyclerView.setAdapter(SearchrecyclerAdapter);

            if (s.toString().equals("")) {
                Search_Linear.setVisibility(View.GONE);
                wrapLinear.setVisibility(View.VISIBLE);
            }

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
                Intent intent = new Intent();
                intent.putExtra("cleanerId", data.getStringExtra("cleanerId"));
                setResult(RESULT_OK,intent);
                finish();
        }
    }

}
