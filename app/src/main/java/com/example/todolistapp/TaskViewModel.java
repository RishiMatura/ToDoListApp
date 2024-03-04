package com.example.todolistapp;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;

public class TaskViewModel extends ViewModel {

    private Context context;
    private DatabaseHelper databaseHelper; // Added DatabaseHelper

    public TaskViewModel(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getDB(context); // Initialize DatabaseHelper
    }

    public void saveTask(String taskText) {
        if (!taskText.isEmpty()) {
            databaseHelper.tasksDAO().insertTask(new Tasks(taskText, 0));
        }
    }
}

