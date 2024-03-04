package com.example.todolistapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TasksDAO {

    @Query("SELECT * FROM Tasks")
    List<Tasks> getAllTasks();

    @Insert
    void insertTask(Tasks task);

    @Update
    void updateTask(Tasks task);

    @Delete
    void deleteTask(Tasks task);

    @Query("DELETE FROM Tasks")
    void deleteAllTasks();
}
