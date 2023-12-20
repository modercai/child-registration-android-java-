package com.example.register_child;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastname, age;
    RadioButton male, female;
    RadioGroup genderSelection;
    Spinner immunization;
    Button submitButton;
    TextView textViewImmunizations;
    boolean[] selectedImmunizations;
    String[] immunizationOptions = {"BCG", "MMR", "RV", "DTaP"};

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
        textViewImmunizations = findViewById(R.id.textViewImmunizations);
        submitButton = findViewById(R.id.buttonSubmit);


        selectedImmunizations = new boolean[immunizationOptions.length];

        textViewImmunizations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImmunizationsDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqLite mySqLite = new MySqLite(RegisterActivity.this);

                String selectedImmunizationText = getSelectedItemsText(immunizationOptions, selectedImmunizations);
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
                    lastname.setError("invalid last name");
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
                        selectedImmunizationText);
            }
        });

    }
    private void showImmunizationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Immunizations");

        builder.setMultiChoiceItems(immunizationOptions, selectedImmunizations, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the selected items array
                selectedImmunizations[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the TextView with selected items
                textViewImmunizations.setText(getSelectedItemsText(immunizationOptions, selectedImmunizations));
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private String getSelectedItemsText(String[] items, boolean[] selectedItems) {
        StringBuilder selectedItemsText = new StringBuilder();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i]) {
                selectedItemsText.append(items[i]);
                if (i < selectedItems.length - 1) {
                    selectedItemsText.append(", ");
                }
            }
        }
        return selectedItemsText.toString();
    }
//    check if the name contains any special characters and/or numbers
    private boolean isValidName(String name) {
        return !name.matches("[a-zA-Z]+");
    }
}