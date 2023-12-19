package com.example.register_child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button go_to_reg;
    RecyclerView recyclerView;

    MySqLite myDb;
    ArrayList<String> child_id,first_name,last_name,child_age,child_gender,child_immunization;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView1);
        go_to_reg = findViewById(R.id.go_to_reg);

        go_to_reg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        myDb = new MySqLite(MainActivity.this);
                child_id = new ArrayList<>();
                first_name = new ArrayList<>();
                last_name = new ArrayList<>();
                child_age = new ArrayList<>();
                child_gender = new ArrayList<>();
                child_immunization = new ArrayList<>();
                storedData();
                customAdapter = new CustomAdapter(MainActivity.this,child_id,first_name,last_name,child_age,child_gender,child_immunization);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
//    store the data in array after reading from the database.
    void storedData(){
        Cursor cursor = myDb.readDatabase();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                child_id.add(cursor.getString(0));
                first_name.add(cursor.getString(1));
                last_name.add(cursor.getString(2));
                child_age.add(cursor.getString(3));
                child_gender.add(cursor.getString(4));
                child_immunization.add(cursor.getString(5));
            }
        }
    }
}