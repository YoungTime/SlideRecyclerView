# SlideRecyclerView
[中文](https://github.com/YoungTime/SlideRecyclerView/blob/master/README_zh.md) | [English](https://github.com/YoungTime/SlideRecyclerView/blob/master/README.md)

描述：SlideRecyclerview 是一个支持左滑菜单栏的 RecyclerView，是一个比较轻量级的自定义 View。

## 优点

轻量：该库比较轻量，不会因为引库导致应用庞杂

简单：使用简单，Activity 内只需要几行代码实现

## 依赖

- 克隆本项目，然后在你的 IDE 中依赖本项目的 slide_recyclerview Module

- 使用 maven 方式依赖

​       在根目录的 build.gradle 中添加：

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

​      在 app 或者要使用的 Module 的 build.gradle 中添加：

```groovy
implementation 'com.github.YoungTime:SlideRecyclerview:1.0.1'
```

## 用法

1. 在布局文件中使用 SlideRecyclerview：

```xm
<com.ryan.slide_recyclerview.SlideRecyclerView
        app:layout_constraintTop_toTopOf="parent"
        android:id="@+id/jsr_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:item_margin_bottom="2dp"
        app:item_margin_right="10dp"
        app:item_margin_left="10dp"
        app:item_padding_right="10dp"
        app:item_background="#FAFD"/>
```

2. 继承 SlideViewAdapter，并实现方法：

```java
public class MyAdapter extends SlideViewAdapter<MsgEntity> {

    /**
     * MsgEntity 为你的数据源数据类型
     */
    private List<MsgEntity> mDatas; // 数据源
    private Context mContext;

    public MyAdapter(List<MsgEntity> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    /**
     * @return 你需要在 Item 中使用的数据列表
     */
    @Override
    protected List<MsgEntity> getDataList() {
        return mDatas;
    }

    /**
     * 绑定 Item 布局，也可根据 data 类型返回不同的 View
     * @param parent 父布局
     * @param data 单个布局的 Item 的数据
     * @return 返回单个 Item 的布局
     */
    @Override
    protected View bindContent(ViewGroup parent, MsgEntity data,int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_view,parent,false);
        // 操作 Item 中的子 View
        TextView textView = view.findViewById(R.id.tv_slide);
        textView.setText(data.getName());
        return view;
    }

    /**
     * @return 需要返回使用 SlideRecyclerview 的上下文，不能为空
     */
    @Override
    protected Context getContext() {
        return mContext;
    }
}
```

3. 在 Activity 中使用：

```java
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
```

4. 给布局使用元素

     如果你需要给 SlideRecyclerView 设置 bg、margin、padding 等，如果你不想你使用的元素作用到 左滑菜单栏 SlideItem，可以直接使用原生的 RecyclerView 自带的布局元素。如果你想布局元素也作用到左滑菜单栏 SlideItem，请使用已下元素。

   | 元素名称            | 作用             |
   | ------------------- | ---------------- |
   | item_margin_left    | 距左边的 margin  |
   | item_margin_right   | 距右边的 margin  |
   | item_margin_top     | 距上边的 margin  |
   | item_margin_bottom  | 距下边的 margin  |
   | item_padding_left   | 距左边的 padding |
   | item_padding_right  | 距右边的 padding |
   | item_padding_top    | 距上边的 padding |
   | item_padding_bottom | 距下边的 padding |
   | item_background     | item 的背景      |
   

# 效果

![gif](C:/Users/duzeming/Desktop/gif.gif)

喜欢的话请给我一个 Star，谢谢！