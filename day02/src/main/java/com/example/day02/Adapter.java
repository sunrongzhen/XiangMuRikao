package com.example.day02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HaSee on 2018/1/25.
 */

public class Adapter extends BaseAdapter {
    private List<SuperBean.DataBean> list;
    private Context context;

    public Adapter(List<SuperBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewhoder hoder;
        if(view==null){
            view=View.inflate(context,R.layout.layout,null);
            hoder=new Viewhoder();

            hoder.tv= (TextView) view.findViewById(R.id.tv);



            view.setTag(hoder);
        }else{
            hoder = (Viewhoder) view.getTag();
        }

        hoder.tv.setText(list.get(i).getTitle());

        return view;
    }


    class Viewhoder{
        TextView tv;
    }
}
