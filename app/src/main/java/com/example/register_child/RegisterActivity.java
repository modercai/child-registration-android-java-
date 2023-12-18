package com.example.register_child;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastname, age;
    RadioButton male, female;
    RadioGroup genderSelection;
    Spinner immunization;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.editTextFirstName);
        lastname = findViewById(R.id.editTextLastName);
        age = findViewById(R.id.editTextAge);
        male = findViewById(R.id.radioButtonMale);
        female = findViewById(R.id.radioButtonFemale);
        genderSelection = findViewById(R.id.radioGroupGender);
        immunization = findViewById(R.id.spinnerImmunizations);
        submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqLite mySqLite = new MySqLite(RegisterActivity.this);
                mySqLite.addChildInfo(firstName.getText().toString().trim(),
                                    lastname.getText().toString().trim(),
                        Integer.valueOf(age.getText().toString().trim()
                        ),genderSelection.toString().trim(),immunization.toString().trim());
            }
        });
    }
}