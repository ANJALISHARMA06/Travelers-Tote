package com.example.travelerstote;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelerstote.Adapter.Adapter;
import com.example.travelerstote.Constants.MyConstants;
import com.example.travelerstote.Data.AppData;
import com.example.travelerstote.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> title;
    List<Integer> images;
   Adapter adapter;
   RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView= findViewById(R.id.recyclerView);

        addAddTitles();
        addAllImages();
        persistAppData();
        database = RoomDB.getInstance(this);
        System.out.println("------------>" + database.mainDao().getAllSelected(false).get(0).getItemname());

        adapter =new Adapter(this,title,images,MainActivity.this);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(this, 2, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager gridLayoutManager = null;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    private static final int TIME_INTERVAL = 2000;

    private long mBackPressed() {
        return 0;
    }

    @Override

    public void onBackPressed() {
        int mBackPressed = 0;
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = (int) System.currentTimeMillis();
    }

    private void persistAppData(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);

        int last = prefs.getInt(AppData.LAST_VERSION,0);
        if(!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE,false)){
            appData.persistAllData();
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE,true);
            editor.commit();

        }else if(last<AppData.NEW_VERSION){
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION,AppData.NEW_VERSION);
            editor.commit();
        }
    }

    private void addAddTitles(){
        title = new ArrayList<>();
        title.add(MyConstants.BASIC_NEEDS_CAMEL_CASE);
        title.add(MyConstants.CLOTHING_CAMEL_CASE);
        title.add(MyConstants.PERSONAL_CARE_CAMEL_CASE);
        title.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        title.add(MyConstants.HEALTH_CAMEL_CASE);
        title.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        title.add(MyConstants.FOOD_CAMEL_CASE);
        title.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE);
        title.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE);
        title.add(MyConstants.NEEDS_CAMEL_CASE);
        title.add(MyConstants.MY_LIST_CAMEL_CASE);
        title.add(MyConstants.MY_SELECTIONS_CAMEL_CASE);


    }
       private void addAllImages(){
        images= new ArrayList<>();
        images.add(R.drawable.p1);
        images.add(R.drawable.p2);
        images.add(R.drawable.p3);
        images.add(R.drawable.p4);
        images.add(R.drawable.p5);
        images.add(R.drawable.p6);
        images.add(R.drawable.p7);
        images.add(R.drawable.p8);
        images.add(R.drawable.p9);
        images.add(R.drawable.p10);
        images.add(R.drawable.p11);
        images.add(R.drawable.p12);
       }
}