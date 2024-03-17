// MainActivity.java
package com.example.todolistapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.example.todolistapp.RecyclerViewFiles.ModelClass;
import com.example.todolistapp.RecyclerViewFiles.ToDoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToDoListAdapter adapter;
    private List <ModelClass> taskList = new ArrayList<>();   // to be used for RecyclerView
    private FloatingActionButton fab;
    private DatabaseHelper databaseHelper; // Database helper instance

//    RelativeLayout rowLayout;
RelativeLayout rowLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view  =getLayoutInflater().inflate(R.layout.task_layout, null);

         rowLayout = view.findViewById(R.id.row_layout);

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

//Log.d("testingRishi", "before");
//
//        rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity.this, "Finally", Toast.LENGTH_SHORT).show();
//                Log.d("testingRishi", "during");
//
//                return true;
//            }
//        });
//
//Log.d("testingRishi", "after");


    }

/*
    Whats happening in this loadTask() function --

    This function's sole purpose is to propagate the recyclerView with the full database for the first time
    as used in the MainActivity. For adding new elements

        1) We create a list of type objects of Tasks.class which contains the getter and setter for database.
        this list contains all the entries from the database.

        2) The loop iterates over each Tasks object retrieved from the database.

        3) Inside the loop each of these tasks are taking place --

            a)  Creating a new instance/object of ModelClass
            b)  We set the values like Id, Task, Status for each object of the ModelClass.
            c)  We add this object named model in the taskList.
 */



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

//    private void addDummyTasks() {
//        ModelClass task = new ModelClass();
//        task.setTask("This is a task");
//        task.setId(1);
//        task.setStatus(0);
//
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);
//
//        adapter.notifyDataSetChanged();
//    }


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

//        taskList.get(view.getId()).setStatus();
//        databaseHelper.tasksDAO().updateTask(new Tasks());
    }

    public void appendToList(String taskText, long autoGenId) {
        ModelClass model = new ModelClass();
        model.setTask(taskText);
        model.setId(autoGenId);
        model.setStatus(0);
        taskList.add(model);
        Log.d("Data", "Entry::: " + model.getId() + "*********************");

    }



}