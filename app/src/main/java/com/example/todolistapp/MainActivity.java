package com.example.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;
import com.example.todolistapp.RecyclerViewFiles.ModelClass;
import com.example.todolistapp.RecyclerViewFiles.ToDoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ToDoListAdapter adapter;
    List<ModelClass> taskList = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ToDoListAdapter(this, taskList);
        recyclerView.setAdapter(adapter);

        // Set a click listener for the FAB button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the bottom sheet dialog fragment to add a new task
                AddNewTaskDialog dialog = AddNewTaskDialog.newInstance();
                dialog.show(getSupportFragmentManager(), "AddNewTaskDialog");
            }
        });

        // Add dummy tasks for testing
        addDummyTasks();
    }

    private void addDummyTasks() {
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
