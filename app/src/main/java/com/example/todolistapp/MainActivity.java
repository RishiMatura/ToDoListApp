// MainActivity.java
package com.example.todolistapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
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
    private DatabaseHelper databaseHelper; // Database helper instance
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ToDoListAdapter(this, taskList);
        recyclerView.setAdapter(adapter);

        databaseHelper = DatabaseHelper.getDB(this);
        loadTasks();





        // Set a click listener for the FAB button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the AddNewTaskDialog
                AddNewTaskDialog dialog = new AddNewTaskDialog(MainActivity.this);
                dialog.show(getSupportFragmentManager(), "AddNewTaskDialog");
            }
        });

        // Add dummy tasks for testing
//        addDummyTasks();
//        loadTasks();

    }


    void loadTasks() {
        List<Tasks> tasks = databaseHelper.tasksDAO().getAllTasks();
        taskList.clear();
        for (Tasks task : tasks) {
            // Convert Tasks object to ModelClass object
            ModelClass model = new ModelClass();
            model.setTask(task.getTask());
            model.setId(task.getId());
            model.setStatus(task.getStatus());
            taskList.add(model);
        }
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(tasks.size());

        recyclerView.scrollToPosition(tasks.size());
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
//    public void onCheckBoxClick(View view) {
//        CheckBox checkBox = view.findViewById(R.id.todoCheckBox);
//        int position = recyclerView.getChildAdapterPosition(view); // Get item position
//
//        if (position != RecyclerView.NO_POSITION) {
//            ModelClass task = taskList.get(position);
//            task.setStatus(checkBox.isChecked() ? 1 : 0); // Update task status
//            adapter.notifyItemChanged(position); // Notify adapter about change
//        }
//    }


    public void onCheckBoxClick(View view) {
        CheckBox checkBox = view.findViewById(R.id.todoCheckBox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    public void appendToList(String taskText) {
        ModelClass model = new ModelClass();
        model.setTask(taskText);
        model.setId(taskList.get(
                taskList.size()-1).getId()+1
        );
        model.setStatus(0);
        taskList.add(model);

    }
}