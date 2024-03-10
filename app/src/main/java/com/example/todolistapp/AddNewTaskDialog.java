package com.example.todolistapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTaskDialog extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;
    private DatabaseHelper databaseHelper; // Database helper instance
    private Context context;

    public AddNewTaskDialog(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        databaseHelper = DatabaseHelper.getDB(getContext());

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        newTaskText = view.findViewById(R.id.newTask);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = newTaskText.getText().toString();
                if (!taskText.isEmpty()) {
                    // Insert task into the database
                    databaseHelper.tasksDAO().insertTask(new Tasks(taskText, 0));

                    dismiss();
//                    ((MainActivity) context).loadTasks();

                    ((MainActivity) context).appendToList(taskText);

                } else {
                    Toast.makeText(getContext(), "Empty Task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}