package com.example.tema2.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mark")
public class Mark {
    @PrimaryKey
    @NonNull
    public String name;
    public Integer mark;
}