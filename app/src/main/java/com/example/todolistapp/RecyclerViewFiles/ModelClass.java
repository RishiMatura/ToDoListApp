package com.example.todolistapp.RecyclerViewFiles;

import android.widget.EditText;

public class ModelClass {
    private long id;
    private int status;

    public ModelClass(String task) {
        this.task = task;
    }

    private String task;

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
