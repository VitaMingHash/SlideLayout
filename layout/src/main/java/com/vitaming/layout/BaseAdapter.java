package com.vitaming.layout;

import android.util.Log;
import android.view.View;

import androidx.annotation.CallSuper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 * 1/5/21
 */
public abstract class BaseAdapter<T> {
    private int layout;
    private OnItemClick<T> onItemClick;
    private List<T> list = new ArrayList<>();
    private List<CacheBean<T>> cacheBeanList = new ArrayList<>();

    public BaseAdapter(int layout) {
        this.layout = layout;
    }

    public BaseAdapter(int layout, List<T> list) {
        this.layout = layout;
        this.list = list;
    }

    public int getLayout() {
        return layout;
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    public List<T> getData() {
        return list;
    }

    public void setOnItemClickListener(OnItemClick<T> onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected void onItemClick(View view, T t) {
        int position = list.indexOf(t);
        for (int i = 0; i < cacheBeanList.size(); i++) {
            for (int j = 0; j < cacheBeanList.get(i).getList().size(); j++) {
                position += 1;
            }
        }
        onItemClick.onClick(t, position, view);
    }

    protected void nextPage(List<ItemPositionBean<T>> itemPositionBeans) {
        List<T> cacheList = new ArrayList<>();
        for (ItemPositionBean<T> itemPositionBean : itemPositionBeans) {
            cacheList.add(itemPositionBean.getT());
            list.remove(itemPositionBean.getT());
        }
        cacheBeanList.add(new CacheBean<>(cacheList));
    }

    protected void lastPage() {
        if (cacheBeanList.size() > 0) {
            List<T> cacheList = cacheBeanList.get(cacheBeanList.size() - 1).getList();
            for (int i = cacheList.size() - 1; i > -1; i--) {
                list.add(0, cacheList.get(i));
            }
            cacheBeanList.remove(cacheBeanList.size() - 1);
        }
    }

    public abstract void convert(ViewHolder viewHolder, T t, int position);

}
