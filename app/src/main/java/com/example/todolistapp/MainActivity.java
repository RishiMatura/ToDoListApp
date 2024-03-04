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

    private RecyclerView recyclerView;
    private ToDoListAdapter adapter;
    private List<ModelClass> taskList = new ArrayList<>();
    private FloatingActionButton fab;
    private TaskViewModel taskViewModel; // Added TaskViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TaskViewModel
        taskViewModel = new TaskViewModel(getApplication());

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
                taskViewModel = new TaskViewModel(getApplication()); // Pass application context

                AddNewTaskDialog dialog = new AddNewTaskDialog(MainActivity.this, taskViewModel); // Pass TaskViewModel
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

        adapter.notifyDataSetChanged();
    }

    // Handle checkbox click event (implement logic to update task status and notify adapter)
    public void onCheckBoxClick(View view) {
        CheckBox checkBox = view.findViewById(R.id.todoCheckBox);
        int position = recyclerView.getChildAdapterPosition(view); // Get item position

        if (position != RecyclerView.NO_POSITION) {
            taskViewModel = new TaskViewModel(getApplication()); // Pass application context

            ModelClass task = taskList.get(position);
            task.setStatus(checkBox.isChecked() ? 1 : 0); // Update task status
            adapter.notifyItemChanged(position); // Notify adapter about change
        }
    }
}
