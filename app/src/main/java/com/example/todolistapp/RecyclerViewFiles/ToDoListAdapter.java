package com.example.todolistapp.RecyclerViewFiles;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.CategoryDialog;
import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    Context context;
    List<ModelClass> taskList;


    DatabaseHelper databaseHelper;

//    public void setTasks(List<ModelClass> tasks) {
//        taskList.clear(); // Clear existing tasks
//        taskList.addAll(tasks); // Add new tasks
//        notifyDataSetChanged(); // Notify adapter of data change
//    }

    public ToDoListAdapter(Context context, List<ModelClass> todoList) {
        this.context = context;
        this.taskList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            // If no payload, bind the entire view
            onBindViewHolder(holder, position);
        } else {
            // If payload exists, update only the specific views affected by the payload
            for (Object payload : payloads) {
                if (payload instanceof TaskPayload) {
                    String taskText = ((TaskPayload) payload).getTaskText();
                    holder.checkBox.setText(taskText);
                }
                // Add more payload handling if needed
            }
        }
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.checkBox.setImageResource(todoList.get(position));
        final ModelClass item = taskList.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        databaseHelper = DatabaseHelper.getDB(context);
        holder.checkBox.setFocusable(false);

        holder.rowLayout.setOnClickListener(getClickListener(holder, position));
        holder.checkBox.setOnClickListener(getClickListener(holder, position));


//        The Long Press Functionality of the rowLayout

        holder.rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openContextMenu(v, position);
                return true;
            }
        });
        holder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openContextMenu(v, position);
                return true;
            }
        });
//        holder.rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//              Used a function to delete the entry and embedded it inside the popup menu
//                return true;
//            }
//        });
    }

    @NonNull
    private View.OnClickListener getClickListener(ViewHolder holder, int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.checkBox.isChecked();
                if (!(v instanceof CheckBox)) {
                    isChecked = !isChecked;
                    holder.checkBox.setChecked(isChecked);
                }
                taskList.get(position).setStatus(isChecked ? 1 : 0);
                long id = taskList.get(position).getId();

                Tasks currentTask = new Tasks();
                currentTask.setId(id);
//                currentTask.setStatus(item.getStatus());

                // Update the status of the current task
                currentTask.setStatus(isChecked ? 1 : 0);
                int status = currentTask.getStatus();
//                currentTask.setStatus(isChecked ? 1 : 0); // Assuming 1 means completed and 0 means incomplete

                // Update the task in the database
                databaseHelper.tasksDAO().updateStatus(id, status);
            }
        };
    }

    //    Function to open a ContextMenu on long Press (TO be Implemented on both  checkBox and the CardLayout)
    public void openContextMenu(View v, int position){

        PopupMenu popupMenu = new PopupMenu(context, v, Gravity.CENTER, 0, R.style.PopupMenuMoreCentralized);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Get the ID of the clicked menu item
                int itemId = item.getItemId();

                // Check the ID of the clicked menu item and perform the corresponding action
                if (itemId == R.id.EditTaskMenu) {
//                  Handle menu option 1 click
                    editTaskFun(position);
                    return true;

                } else if (itemId == R.id.DeleteTaskMenu) {
//                  Handle menu option 2 click
                    deleteTaskFun(position);
                    return true;

                } else if (itemId == R.id.CategoryTaskMenu) {
//                  Handling event when show category is implemented
                    selectCategory(position);
                    return true;
                }


                return false; // Return false if the clicked item is not handled
            }
        });
        popupMenu.show();
    }
    
    //Function to delete task and its reference is used inside the context menu (popup Menu)
    public void deleteTaskFun(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Delete Task Entry?")
                .setIcon(R.drawable.baseline_delete_24)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ModelClass task = taskList.get(position);

                        // Remove the task from the task list
                        taskList.remove(position);
                        notifyItemRemoved(position);

                        // Delete the task from the database
                        Tasks tasksToDelete = new Tasks();
                        tasksToDelete.setId(task.getId()); // Assuming getId returns the task ID
                        long id = task.getId();
//                                Log.d("Database", "Entry::: " + taskList.get(position).getId() + "*********************");

                        databaseHelper.tasksDAO().deleteTaskById(id);
//                                databaseHelper.tasksDAO().deleteTask(tasksToDelete);

                        notifyItemRangeChanged(position, taskList.size());

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Be Careful !", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }


//    Function to edit the Tasks from the popup menu
    public void editTaskFun(int position){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.update_task_layout);

        EditText editTask = dialog.findViewById(R.id.dialogBoxEditTask);
        Button dialogBoxBtnAction = dialog.findViewById(R.id.dialogBoxBtnAction);

//        Setting the text in the editText field from the pre-existing recyclerView
        editTask.setText(taskList.get(position).getTask());


        dialogBoxBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = taskList.get(position).getId();
                String edTaskTxt = editTask.getText().toString();
//                Updating the Database
                taskList.get(position).setTask(edTaskTxt);

//                Updating the RecyclerView
//                notifyItemChanged(position);
                notifyItemChanged(position, new TaskPayload(edTaskTxt));

                databaseHelper.tasksDAO().updateTaskString(id, edTaskTxt);

                dialog.dismiss();

            }
        });
        dialogBoxBtnAction.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Click to update Details", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        dialog.show();

    }

//    Function to Select Category from the context menu
    public void selectCategory(int position){
//        String[] categories = {"Food", "Shopping", "Work", "Personal", "Health"};
        ArrayList<String> categoriesList = new ArrayList<String>();
        categoriesList.add("Food");
        categoriesList.add("Shopping");
        categoriesList.add("Work");
        categoriesList.add("Personal");
        categoriesList.add("Health");
        CategoryDialog.showCategoryDialog(context, categoriesList, taskList, position, new CategoryDialog.OnCategorySelectedListener() {
            @Override
            public void onCategorySelected(String category) {
                long id = taskList.get(position).getId();
                String tasktxt = taskList.get(position).getTask();

//                Separate DAO Query for Updating Categories
                databaseHelper.tasksDAO().updateCategory(id, category);
//                notifyDataSetChanged();         //Absolutely Not Recommended to use this function
//                No need to create a separate obj of Tasks
//                Tasks updateCategory = new Tasks();
//                updateCategory.setId(id);
//                updateCategory.setCategories(category);

//                databaseHelper.tasksDAO().updateCategory(id, category);
//                notifyDataSetChanged(); // Notify adapter to reflect changes
            }
        });
    }

    private boolean toBoolean(int n){
        return (n!=0);

    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        RelativeLayout rowLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.todoCheckBox);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
 class TaskPayload {
    private String taskText;

    public TaskPayload(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskText() {
        return taskText;
    }
}

