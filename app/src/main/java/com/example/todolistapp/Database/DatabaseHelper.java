package com.example.todolistapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolistapp.RecyclerViewFiles.ModelClass;

@Database(entities = {Tasks.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DATABASE_NAME = "tasks_database";
    private static DatabaseHelper instance;

    public abstract TasksDAO tasksDAO();

    public static synchronized DatabaseHelper getDB(Context context) {
        if (instance == null) {
                    instance = Room.databaseBuilder(context, DatabaseHelper.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
