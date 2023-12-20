package com.example.register_child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    Button go_to_reg,sort_by_name, sort_by_age;
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

        sort_by_age = findViewById(R.id.sort_by_age);
        sort_by_name = findViewById(R.id.sort_by_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//layout for a divider in recycler view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        sort_by_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByAge();
            }
        });

        sort_by_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByName();
            }
        });

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
//    sorting methods(having the functionality for sorting by age and sorting by name)
    private void sortByAge() {
        // Implement custom sorting logic for child_age ArrayList
        Collections.sort(child_age, new Comparator<String>() {
            @Override
            public int compare(String age1, String age2) {

                return Integer.compare(Integer.parseInt(age1), Integer.parseInt(age2));
            }
        });
        customAdapter.notifyDataSetChanged();
    }

    private void sortByName() {
        // Implement custom sorting logic for last_name ArrayList and first_name ArrayList together

        ArrayList<String> combinedNames = new ArrayList<>();

        // Combine last name and first name into a single string for each item
        for (int i = 0; i < last_name.size(); i++) {
            String combinedName = last_name.get(i) + " " + first_name.get(i);
            combinedNames.add(combinedName);
        }

        // Sort the combined names
        Collections.sort(combinedNames, String.CASE_INSENSITIVE_ORDER);

        // Update the original ArrayLists based on the sorted combined names
        for (int i = 0; i < combinedNames.size(); i++) {
            String combinedName = combinedNames.get(i);
            String[] names = combinedName.split(" ");

            // Update last_name and first_name ArrayLists
            last_name.set(i, names[0]);
            first_name.set(i, names[1]);
        }

        // Notify the adapter about the changes in the data
        customAdapter.notifyDataSetChanged();
    }

}