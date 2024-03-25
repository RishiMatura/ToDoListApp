package com.example.todolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CategoryDialog {
//    private String[] categories = {"Food", "Groceries", "Work", "Personal", "Health"};

    public interface OnCategorySelectedListener {
        void onCategorySelected(String category);
    }

    public static void showCategoryDialog(Context context, String[] categories, final OnCategorySelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Category")
                .setItems(categories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedCategory = categories[which];
                        if (listener != null) {
                            listener.onCategorySelected(selectedCategory);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
