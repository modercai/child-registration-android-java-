package com.example.register_child;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChildProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        TextView textViewFirstName = findViewById(R.id.textViewFirstName);
        TextView textViewLastName = findViewById(R.id.textViewLastName);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewGender = findViewById(R.id.textViewGender);
        TextView textViewImmunizations = findViewById(R.id.textViewImmunizations);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("FIRST_NAME");
        String lastName = intent.getStringExtra("LAST_NAME");
        String age = intent.getStringExtra("AGE");
        String gender = intent.getStringExtra("GENDER");
        String immunizations = intent.getStringExtra("IMMUNIZATION");

        textViewFirstName.setText("First Name: " + firstName);
        textViewLastName.setText("Last Name: " + lastName);
        textViewAge.setText("Age: " + age);
        textViewGender.setText("Gender: " + gender);
        textViewImmunizations.setText("Immunizations: " + immunizations);
    }
}