package com.example.todolistapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolistapp.Database.DatabaseHelper;
import com.example.todolistapp.Database.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTaskDialog extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;

    public static AddNewTaskDialog newInstance() {
        return new AddNewTaskDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        newTaskText = view.findViewById(R.id.newTask);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = newTaskText.getText().toString();

                    Context context = requireActivity(); // Use requireActivity() for non-null context
                    DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
                    databaseHelper.tasksDAO().insertTask(new Tasks(taskText, 0));
                    // Call saveTask with the obtained context
                    saveTask(context, taskText);


                    dismiss();
            }
        });

        return view;
    }

    private void saveTask(Context context, String taskText) {
        // Implement logic to save the new task to the database using your DatabaseHelper
        // or another appropriate approach.
//        databaseHelper.tasksDAO().insertTask(new Tasks(taskText, 0));
//    }
}}

