package com.example.todolistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.todolistapp.RecyclerViewFiles.ModelClass;

import java.util.ArrayList;
import java.util.List;

public class CategoryDialog {
//    private String[] categories = {"Food", "Groceries", "Work", "Personal", "Health"};

    public interface OnCategorySelectedListener {
        void onCategorySelected(String category);
    }

    public static void showCategoryDialog(Context context, ArrayList<String> categories, List<ModelClass> taskList, int position,String currentCategory, final OnCategorySelectedListener listener) {
//        String[] categories = {"Food", "Shopping", "Work", "Personal", "Health"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_category_selection, null);
        builder.setView(dialogView);

        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);

        for (String category : categories) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(category);
            radioGroup.addView(radioButton);
        }

        builder.setTitle("Select Category")
                .setPositiveButton("OK", (dialog, which) -> {
                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
                    if (selectedRadioButton != null) {
                        String selectedCategory = selectedRadioButton.getText().toString();
                        if (listener != null) {
                            listener.onCategorySelected(selectedCategory);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Add New Category");


                        // Create an EditText to input the new category
                        final EditText input = new EditText(context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);


                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newCategory = input.getText().toString().trim();
                                if (!TextUtils.isEmpty(newCategory)) {
                                    categories.add(newCategory);

                                    // Add the new category to the list of categories
                                    long id = taskList.get(position).getId();
                                    String tasktxt = taskList.get(position).getTask();


                                    // Process the new category (e.g., add it to the database)
                                    if (listener != null) {
                                        listener.onCategorySelected(newCategory);
                                    }

                                } else {
                                    // Show a message if the input is empty
                                    Toast.makeText(context, "Please enter a category", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

                    }
                });
        builder.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Select Category")
//                .setItems(categories, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String selectedCategory = categories[which];
//                        if (listener != null) {
//                            listener.onCategorySelected(selectedCategory);
//                        }
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();

        // Pre-select the current category, if it exists
        int selectedPosition = categories.indexOf(currentCategory);
        if (selectedPosition >= 0) {
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getChildAt(selectedPosition);
            selectedRadioButton.setChecked(true);
        }

    }
}
