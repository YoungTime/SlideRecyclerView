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
        final List<MsgEntity> list = new ArrayList<>();

        /**
         * 这里是模拟的数据源
         */
        MsgEntity entity1 = new MsgEntity();
        entity1.setName("第一个");
        MsgEntity entity2 = new MsgEntity();
        entity2.setName("第二个");
        MsgEntity entity3 = new MsgEntity();
        entity3.setName("第三个");
        MsgEntity entity4 = new MsgEntity();
        entity4.setName("第四个");
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        list.add(entity4);


        MyAdapter adapter = new MyAdapter(list,this);
        /**
         * 支持的 SlideItem，可以自定义 Item 背景，textColor，textSize，以及点击事件
         * 背景暂时只支持 ResourceID
         */
        adapter.addSlideItem(new SlideItem("Edit", R.drawable.edit,new SlideItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"Edit "+list.get(pos).getName(),Toast.LENGTH_SHORT).show();
            }
        })).addSlideItem(new SlideItem("Delete",  R.drawable.delete,R.color.colorAccent,0,0, new SlideItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"Delete "+list.get(pos).getName(),Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerView.setAdapter(adapter);

    }
}
