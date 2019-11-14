# SlideRecyclerView
[中文](./README_zh) | [English](./README)

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
implementation 'com.github.YoungTime:SlideRecyclerview:1.0.0'
```

## 用法

1. 在布局文件中使用 SlideRecyclerview：

```xm
<com.ryan.slide_recyclerview.SlideRecyclerView
        android:id="@+id/jsr_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

2. 继承 SlideViewAdapter，并实现方法：

```java
public class MyAdapter extends SlideViewAdapter {

    private List<Object> mDatas;
    private Context mContext;
    
    /**
     * @return 你需要在 Item 中使用的数据列表
     */
    @Override
    protected List<Object> getDataList() {
        return mDatas;
    }

    /**
     * 绑定 Item 布局
     * @param parent 父布局
     * @param data 单个布局的 Item 的数据
     * @return 返回单个 Item 的布局
     */
    @Override
    protected View bindContent(ViewGroup parent, Object data) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xxx,parent,false);
        // 操作 Item 中的子 View
        TextView textView = view.findViewById(R.id.xxx);
        textView.setText((String) data);
        return view;
    }

    /**
     * @return 需要返回使用 SlideRecyclerview 的上下文
     */
    @Override
    protected Context getContext() {
        return mContext;
    }
}
```

3. 在 Activity 中使用：

```java
   
```

   
