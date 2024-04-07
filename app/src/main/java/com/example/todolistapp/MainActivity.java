// MainActivity.java
package com.example.todolistapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
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
    private ImageButton filterButton;

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


        filterButton = findViewById(R.id.filter_Button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });


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
    public void showPopUp(View v) {

        PopupMenu popup = new PopupMenu(this, v);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_all) {
                    // Show all tasks
                    loadTasks();
//                    List<Tasks> allTasks = databaseHelper.tasksDAO().getAllTasks();

                    return true;
                } else if (id == R.id.menu_food) {
                    // Show tasks of Food
                    loadTasksByCategory("Food");
                    return true;
                } else if (id == R.id.filter_shopping) {
                    // Show tasks of Shopping
                    loadTasksByCategory("Shopping");
                    return true;
                } else if (id == R.id.filter_work) {
                    // Show tasks of Work
                    loadTasksByCategory("Work");
                    return true;
                } else if (id == R.id.filter_personal) {
                    // Show tasks of Personal
                    loadTasksByCategory("Personal");
                    return true;
                } else if (id == R.id.filter_health) {
                    // Show tasks of Health
                    loadTasksByCategory("Health");
                    return true;
                }
                // If the selected item doesn't match any case, let the superclass handle it
                        return false;
                    }
                });
                popup.inflate(R.menu.filter_by_category);
                popup.show();
        }
    private void loadTasksByCategory(String category) {
        List<Tasks> filteredTasks = databaseHelper.tasksDAO().getTasksByCategory(category);
        updateRecyclerView(filteredTasks);
    }
public void updateRecyclerView(List<Tasks> filteredTasks){
    taskList.clear();
    for (Tasks task : filteredTasks) {
        ModelClass model = new ModelClass();
        model.setTask(task.getTask());
        model.setId(task.getId());
        model.setStatus(task.getStatus());
        taskList.add(model);
    }
    adapter.notifyDataSetChanged();


}
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.menu_all) {
//            // Show all tasks
//            Toast.makeText(this, "Option1", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.menu_food) {
//            // Show tasks of category 1
//            Toast.makeText(this, "Option2", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.menu_shopping) {
//            // Show tasks of category 2
//            Toast.makeText(this, "Option3", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        // If the selected item doesn't match any case, let the superclass handle it
//        return super.onOptionsItemSelected(item);
//    }

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




    public void appendToList(String taskText, long autoGenId) {
        ModelClass model = new ModelClass();
        model.setTask(taskText);
        model.setId(autoGenId);
        model.setStatus(0);
        taskList.add(model);
        Log.d("Data", "Entry::: " + model.getId() + "*********************");

    }
//    public void showCategorySelectionFragment() {
//        CategorySelectionFragment fragment = new CategorySelectionFragment();
//        fragment.setOnCategorySelectedListener(new CategorySelectionFragment.OnCategorySelectedListener() {
//            @Override
//            public void onCategorySelected(String category) {
//                // Handle the selected category
//                // For example, update the UI to display the selected category
//            }
//        });
//        getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, fragment)
//                .addToBackStack(null)
//                .commit();
//    }

}