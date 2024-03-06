package com.example.todolistapp.RecyclerViewFiles;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    Context context;
    private List<ModelClass> taskList;

//    public void setTasks(List<ModelClass> tasks) {
//        taskList.clear(); // Clear existing tasks
//        taskList.addAll(tasks); // Add new tasks
//        notifyDataSetChanged(); // Notify adapter of data change
//    }

    public ToDoListAdapter(Context context, List<ModelClass> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    List<ModelClass> todoList;
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
        final ModelClass item = todoList.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));

//        holder.rowLayout.

    }
private boolean toBoolean(int n){
    return (n!=0);

}
    @Override
    public int getItemCount() {
        return todoList.size();
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
