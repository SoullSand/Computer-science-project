package com.example.computerscienceproject;

import com.google.android.gms.common.internal.Objects;

public class User {
    private String name;
    private int easyRecord, mediumRecord, hardRecord;

    public User(String name)
    {
        this.name = name;
        easyRecord = -1;
        mediumRecord = -1;
        hardRecord = -1;
    }

    @Override
    public String toString()
    {
        String userStats = name;
        userStats += ";" + easyRecord;
        userStats += ";" + mediumRecord;
        userStats += ";" + hardRecord;
        return userStats;
    }
}
