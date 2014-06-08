package com.shopping.list;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LlistaAdapter extends BaseAdapter {

    private ArrayList<String> array;
    private ArrayList<Boolean> striked;
    private Manager manager;

    public LlistaAdapter(Context context) {
        manager = new Manager(context);
        array = manager.getLlista();
        striked = manager.getStriked();
    }

    public int getCount() {
        return array.size();
    }

    public String getItem(int position) {
        return array.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout ll = new LinearLayout(parent.getContext());
        ll.setPadding(0, 20, 0, 20);
        TextView tv = new TextView(parent.getContext());
        tv.setText(array.get(position));
        if (striked.get(position))
            ll.setBackgroundColor(Color.GREEN);
        else
            ll.setBackgroundColor(Color.RED);
        //			tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ll.addView(tv);
        return ll;
    }


    public void update() {
        array.clear();
        array = manager.getLlista();
        striked = manager.getStriked();
    }

}
