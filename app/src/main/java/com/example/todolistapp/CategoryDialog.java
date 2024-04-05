package com.example.todolistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

public class CategoryDialog {
//    private String[] categories = {"Food", "Groceries", "Work", "Personal", "Health"};

    public interface OnCategorySelectedListener {
        void onCategorySelected(String category);
    }

    public static void showCategoryDialog(Context context, String[] categories, final OnCategorySelectedListener listener) {
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
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = dialogView.findViewById(selectedRadioButtonId);
                        if (selectedRadioButton != null) {
                            String selectedCategory = selectedRadioButton.getText().toString();
                            if (listener != null) {
                                listener.onCategorySelected(selectedCategory);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.show();

//        AlertDialog dialog = builder.create();
//        dialog.show();
//
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
    }
}
