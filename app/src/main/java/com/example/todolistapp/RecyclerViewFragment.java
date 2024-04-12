package com.example.todolistapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.example.todolistapp.RecyclerViewFiles.ModelClass;
import com.example.todolistapp.RecyclerViewFiles.ToDoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment{
    private RecyclerView recyclerView;
    private ToDoListAdapter adapter;
    public  List<ModelClass> taskList = new ArrayList<>();   // to be used for RecyclerView
    private FloatingActionButton fab;
    private DatabaseHelper databaseHelper; // Database helper instance
    private ImageButton filterButton;
    private RelativeLayout rowLayout;


    Context context;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recycler_view, container, false);
        View taskLayout = inflater.inflate(R.layout.task_layout, container, false);

        rowLayout = taskLayout.findViewById(R.id.row_layout);

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));  //context taken

        adapter = new ToDoListAdapter(context, taskList);
        recyclerView.setAdapter(adapter);

        databaseHelper = DatabaseHelper.getDB(context);
        loadTasks();


        filterButton = view.findViewById(R.id.filter_Button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof TaskDialogListener) {
                    TaskDialogListener listener = (TaskDialogListener) getActivity();
                    new AddNewTaskDialog(listener).show(
                            getChildFragmentManager(), "AddNewTaskDialog");
                }

                else {
                    // Handle case where activity doesn't implement the listener
                    Log.e("RecyclerViewFragment", "Activity doesn't implement TaskDialogListener");
                }
                // Show the AddNewTaskDialog
//                AddNewTaskDialog dialog = new AddNewTaskDialog(context);
//                new AddNewTaskDialog(context).show(
//                        getChildFragmentManager(), "AddNewTaskDialog");
//                dialog.show(getChildFragmentManager(), "AddNewTaskDialog");
            }
        });

        return view;
    }
    public void showPopUp(View v) {

        PopupMenu popup = new PopupMenu(context, v);
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

    public void appendToList(String taskText, long autoGenId) {
        ModelClass model = new ModelClass();
        model.setTask(taskText);
        model.setId(autoGenId);
        model.setStatus(0);
        taskList.add(model);
        adapter.notifyItemInserted(taskList.size() - 1);
        Log.d("Data", "Entry::: " + model.getId() + "*********************");
        Log.d("Data", "Added task: " + taskText); // Logging for debugging

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}