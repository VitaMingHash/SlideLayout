package com.vitaming.slidelayout;

import com.vitaming.layout.BaseAdapter;
import com.vitaming.layout.ViewHolder;

import java.util.List;

/**
 * @author Ming
 * 1/5/21
 */
public class MyAdapter extends BaseAdapter<String> {

    public MyAdapter(int layout, List list) {
        super(layout, list);
    }


    @Override
    public void convert(ViewHolder viewHolder, String string, int position) {
        viewHolder.setText(R.id.text, string);
    }
}
