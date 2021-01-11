package com.vitaming.layout;


import android.view.View;

/**
 * @author Ming
 * 2020-08-11
 */
public interface OnItemClick<T> {
    void onClick(T t, int position, View view);
}
