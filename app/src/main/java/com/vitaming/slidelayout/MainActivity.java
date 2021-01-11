package com.vitaming.slidelayout;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.vitaming.layout.OnItemClick;
import com.vitaming.layout.SlideLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private SlideLayout slidelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidelayout = findViewById(R.id.slidelayout);
        List<String> list = new ArrayList<>();
        list.add("1111111");
        list.add("2222222");
        list.add("3333333");
        list.add("4444444");
        list.add("5555555");
        list.add("6666666");
        list.add("7777777");
        list.add("8888888");
        list.add("9999999");
        list.add("0000000");
        list.add("1111111");
        list.add("2222222");
        list.add("3333333");
        list.add("4444444");
        list.add("5555555");
        list.add("6666666");
        list.add("7777777");
        list.add("8888888");
        list.add("9999999");
        list.add("0000000");
        list.add("1111111");
        list.add("2222222");
        list.add("3333333");
        MyAdapter myAdapter = new MyAdapter(R.layout.items, list);
        slidelayout.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new OnItemClick<String>() {
            @Override
            public void onClick(String s, int position, View view) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}