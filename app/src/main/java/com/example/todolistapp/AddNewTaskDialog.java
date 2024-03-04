package com.example.todolistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTaskDialog extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;


    public static AddNewTaskDialog newInstance(){
        return new AddNewTaskDialog();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        newTaskText = view.findViewById(R.id.tasksText);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = newTaskText.getText().toString();
                if (!taskText.isEmpty()) {
                    // Call a method to save the new task to the database
                    saveTask(taskText);
                    // Dismiss the bottom dialog fragment
                    dismiss();
                } else {
                    // Show a message or handle the case where the task text is empty
                }
            }
        });

        return view;
    }

    private void saveTask(String taskText) {
        // Implement logic to save the new task to the database
    }
}
