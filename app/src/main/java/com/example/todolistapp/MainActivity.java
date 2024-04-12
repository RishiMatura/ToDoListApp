// MainActivity.java
package com.example.todolistapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements TaskDialogListener  {

    ViewPager2 viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tab);
        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("ADD TASKS");
            } else if (position == 1) {
                tab.setText("TBD");
            }
        }).attach();

    }


    @Override
    public void onTaskAdded(String taskText, long generatedId) {


        // Handle task addition in the activity (optional)
        // You can forward the information to the fragment here
        RecyclerViewFragment fragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRecyclerView);
        if (fragment != null) {
            Log.d("rishiok", "****************before ");
            fragment.appendToList(taskText, generatedId);
            Log.d("rishiok", "****************after ");

        }
        else {
            Log.d("rishiok", "**************ELSE");
        }
    }

    public void add(String taskText, long autoGenId){

        RecyclerViewFragment fragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRecyclerView);
        if (fragment !=null){
            fragment.appendToList(taskText, autoGenId);
        }
//        RecyclerViewFragment obj = new RecyclerViewFragment();
//        List<ModelClass> taskList = obj.taskList;
//        ModelClass model = new ModelClass();
//        model.setTask(taskText);
//        model.setId(autoGenId);
//        model.setStatus(0);
//        taskList.add(model);
//        obj.appendToList(taskText, autoGenId);
    }
}