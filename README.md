# SlideRecyclerView
[中文](https://github.com/YoungTime/SlideRecyclerView/blob/master/README_zh.md) | [English](https://github.com/YoungTime/SlideRecyclerView/blob/master/README.md)

Description: A  light library of Recyclerview which support left slide to open menu.

## Advantage

Light: The library is very light, wont make your application huger

Easy : Very easy to use, just need several lines code in Activity  

## Dependency

- clone this project, and use the module of slide_recyclerview as dependency

- dependency by maven

​        add following code in build.gradle of root directory:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

​        add the dependency in build.gradle of the module which you need to use:

```groovy
implementation 'com.github.YoungTime:SlideRecyclerview:1.0.1'
```

