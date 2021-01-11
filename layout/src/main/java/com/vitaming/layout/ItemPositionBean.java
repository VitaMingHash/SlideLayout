package com.vitaming.layout;

import android.view.View;

/**
 * @author Ming
 * 1/5/21
 */
public class ItemPositionBean<T> {
    private int top;
    private int left;
    private int bottom;
    private int right;
    private View view;
    private int positon;
    private int page;
    private T t;

    public ItemPositionBean(int top, int left, int bottom, int right, View view, int positon, int page, T t) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.view = view;
        this.positon = positon;
        this.page = page;
        this.t = t;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "[positon=" + positon +
                ", page=" + page +
                ", left=" + left +
                ", right=" + right +
                ", t=" + t.toString() +
                ']';
    }
}
