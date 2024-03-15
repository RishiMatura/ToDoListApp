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
    long insertTask(Tasks task);

    @Update
    void updateTask(Tasks task);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteTaskById(long id);

    @Query("UPDATE tasks SET tasks = :newTask WHERE id = :taskId")
    void updateTaskById(long taskId, String newTask);
}
