package com.ryan.slideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ryan.slideapp.recycler.JRSlideRecyclerView;
import com.ryan.slideapp.recycler.SlideItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private JRSlideRecyclerView recyclerView;


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
        adapter.addSlideItem(new SlideItem("测试", 0, 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击按钮",Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerView.setAdapter(adapter);
    }
}
