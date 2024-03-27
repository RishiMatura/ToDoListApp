package com.example.todolistapp.Database;

import androidx.room.Dao;
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
    void updateTaskString( long taskId, String newTask);

    @Query("UPDATE tasks SET categories = :newCategory WHERE id = :taskId")
    void updateCategory(long taskId, String newCategory);

//    @Query("SELECT * FROM Tasks WHERE id = :id")
//    Tasks getTaskById(long id);
    @Query("UPDATE tasks SET status = :newStatus WHERE id = :taskId")
    void updateStatus(long taskId, int newStatus);



}
