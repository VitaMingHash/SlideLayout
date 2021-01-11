package com.vitaming.layout;

import java.util.List;

/**
 * @author Ming
 * 1/5/21
 */
public class CacheBean<T> {
    private List<T> list;

    public CacheBean() {
    }

    public CacheBean(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CacheBean{" +
                "list=" + list +
                '}';
    }
}
