package com.example.todolistapp.RecyclerViewFiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.R;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.checkBox.setImageResource(todoList.get(position));
        final ModelClass item = taskList.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        databaseHelper = DatabaseHelper.getDB(context);


        holder.rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

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
                return true;
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.todoCheckBox);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
