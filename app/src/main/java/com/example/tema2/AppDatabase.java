package com.example.tema2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tema2.dao.MarkDAO;
import com.example.tema2.models.Mark;

@Database(entities = {Mark.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MarkDAO markDAO();
}