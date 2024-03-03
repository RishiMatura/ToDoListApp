package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.todolistapp.RecyclerViewFiles.ModelClass;
import com.example.todolistapp.RecyclerViewFiles.ToDoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ToDoListAdapter adapter;
    List<ModelClass> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ToDoListAdapter(this, taskList);
        recyclerView.setAdapter(adapter);

        ModelClass task = new ModelClass();
        task.setTask("This is a task");
        task.setId(1);
        task.setStatus(0);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

//        adapter.notifyDataSetChanged();
    }

    public void onCheckBoxClick(View view) {
        CheckBox checkBox = view.findViewById(R.id.todoCheckBox);
        checkBox.setChecked(!checkBox.isChecked());
    }

}