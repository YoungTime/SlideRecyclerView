package com.ryan.slideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ryan.slide_recyclerview.SlideRecyclerView;

import java.util.List;

public class SlideActivity extends AppCompatActivity {

    private SlideRecyclerView rvSlide;
    private MyAdapter adapter;
    private List<Object> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        rvSlide = findViewById(R.id.rv_slide);
        adapter = new MyAdapter(this,dataList);


    }
}
