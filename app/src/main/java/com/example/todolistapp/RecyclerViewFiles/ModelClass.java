package com.example.todolistapp.RecyclerViewFiles;

import android.widget.EditText;

public class ModelClass {
    private long id;
    private int status;


    private String task;

    public ModelClass(long id, String task) {
        this.task = task;
        this.id = id;
    }
    public ModelClass() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
