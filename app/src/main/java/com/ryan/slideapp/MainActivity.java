package com.ryan.slideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ryan.slide_recyclerview.SlideItem;
import com.ryan.slide_recyclerview.SlideItemAdapter;
import com.ryan.slide_recyclerview.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private SlideRecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.jsr_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Object> list = new ArrayList<Object>();
        list.add("第一个");
        list.add("第二个");
        list.add("第三个");
        list.add("第四个");
        list.add("第四个");
        list.add("第四个");
        list.add("第四个");
        list.add("第四个");
        list.add("第四个");
        ShareAdapter adapter = new ShareAdapter(this,list);
        adapter.addSlideItem(new SlideItem("测试", 0, new SlideItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"第"+pos+" 点击按钮",Toast.LENGTH_SHORT).show();
            }
        })).addSlideItem(new SlideItem("测试",  0,R.color.colorPrimaryDark,0,0, new SlideItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"第"+pos+" 点击按钮",Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerView.setAdapter(adapter);
    }
}
