package com.ryan.slideapp;

public class UserInfo {

    private String name;
    private int avatarId;

    public UserInfo(String name,int avatarId){
        this.name = name;
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public int getAvatarId() {
        return avatarId;
    }
}
