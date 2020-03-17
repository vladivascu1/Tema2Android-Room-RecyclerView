package com.example.tema2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.tema2.models.Mark;

@Dao
public interface MarkDAO {
    @Query("SELECT * FROM Mark ORDER BY mark DESC")
    List<Mark> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Mark... marks);
    @Delete
    void delete(Mark mark);
    @Query("SELECT * FROM Mark WHERE name LIKE :name LIMIT 1")
    Mark findByName(String name);
}
