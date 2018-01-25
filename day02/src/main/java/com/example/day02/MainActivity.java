package com.example.day02;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements IMview,XListView.IXListViewListener{

    private XListView mXlist;
    private Handler handler=new Handler();
    private int a=1;
    private List<SuperBean.DataBean> lists;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        getURL(a,this);
        mXlist.setPullLoadEnable(true);
        mXlist.setXListViewListener(this);


    }

    private void initView() {
        mXlist = (XListView) findViewById(R.id.xlist);
    }
    public void getURL(int i, final IMview im){

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request=new Request.Builder()
                .get()
                .url("http://120.27.23.105/product/getProducts?pscid=39&page="+i)
                .build();


        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        im.shibai(e.getMessage());

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){
                     String str = response.body().string();
                    Gson gson = new Gson();
                    SuperBean superBean = gson.fromJson(str, SuperBean.class);
                    final List<SuperBean.DataBean> list = superBean.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        im.cheng(list);
                        }
                    });

                }
            }
        });

    }
    public void onLoad(){
        mXlist.stopLoadMore();
        mXlist.stopRefresh();
        mXlist.setRefreshTime("刚刚");
    }


    @Override
    public void cheng(List<SuperBean.DataBean> data) {
        if (a==1){
            this.lists=data;
        }else{
            lists.addAll(data);
        }


        adapter = new Adapter(lists, MainActivity.this);
        mXlist.setAdapter(adapter);


    }

    @Override
    public void shibai(String msg) {

    }

    @Override
    public void onRefresh() {
        Toast.makeText(this,"下拉刷新",Toast.LENGTH_SHORT).show();
        a=1;
        getURL(a,this);
        onLoad();
    }

    @Override
    public void onLoadMore() {
        //sa
        Toast.makeText(this,"上拉加载",Toast.LENGTH_SHORT).show();
        a++;
        getURL(a,this);
        adapter.notifyDataSetChanged();
        onLoad();
    }
}
interface IMview{
    void cheng(List<SuperBean.DataBean> data);
    void shibai(String msg);
}
