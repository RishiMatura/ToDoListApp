package com.example.todolistapp.RecyclerViewFiles;

public class ModelClass {
    private long id;
    private int status;
    private String categories;

    public ModelClass(long id, String task, String categories) {
        this.id = id;
        this.task = task;
        this.categories = categories;
    }


    public ModelClass(String categories) {
        this.categories = categories;
    }

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
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

}
