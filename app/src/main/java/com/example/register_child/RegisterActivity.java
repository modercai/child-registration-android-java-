package com.example.register_child;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    String[] immunizationOptions = {"Option 1", "Option 2", "Option 3"};

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

//      show the immunization options for the BCG using the below adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, immunizationOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        immunization.setAdapter(spinnerAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqLite mySqLite = new MySqLite(RegisterActivity.this);

                String selectedImmunization = immunization.getSelectedItem().toString();
                // Fetch selected gender
                String selectedGender;

                // Validate First Name
                String firstNameInput = firstName.getText().toString().trim();
                if (firstNameInput.isEmpty()) {
                    firstName.setError("First Name cannot be empty");
                    return; // Stop execution if the input is invalid
                } else if (!isValidName(String.valueOf(firstName))) {
                    firstName.setError("invalid first name");
                }

                // Validate Last Name
                String lastNameInput = lastname.getText().toString().trim();
                if (lastNameInput.isEmpty()) {
                    lastname.setError("Last Name cannot be empty");
                    return;
                } else if (!isValidName(String.valueOf(lastname))) {
                    firstName.setError("invalid last name");
                }

                // Validate Age
                String ageInput = age.getText().toString().trim();
                if (ageInput.isEmpty()) {
                    age.setError("Age cannot be empty");
                    return;
                }
                int ageValue;
                try {
                    ageValue = Integer.parseInt(ageInput);
                } catch (NumberFormatException e) {
                    age.setError("Invalid Age");
                    return;
                }

                int selectedRadioButtonId = genderSelection.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    selectedGender = selectedRadioButton.getText().toString();
                } else {
                    // Handle case where no radio button is selected
                    selectedGender = "";
                }
                mySqLite.addChildInfo(
                        firstName.getText().toString().trim(),
                        lastname.getText().toString().trim(),
                        Integer.valueOf(age.getText().toString().trim())
                        ,selectedGender,
                        selectedImmunization);
            }
        });

    }
//    check if the name contains any special characters and/or numbers
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}