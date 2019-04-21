package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***** Registration page - step 1 *****/

public class CreateAccount1Activity extends AppCompatActivity {

    //UI references
    private AutoCompleteTextView firstNameACTV;
    private EditText surnameET;
    //private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account1);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        firstNameACTV = findViewById(R.id.firstNameACTV);
        surnameET = findViewById(R.id.surnameEditText);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptRegisterName()) {
                    Snackbar.make(view, "Launching CreateAccount2Activity.class", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent launchactivity = new Intent(getApplicationContext(), CreateAccount2Activity.class);

                    String firstName = firstNameACTV.getText().toString();
                    String surName = surnameET.getText().toString();
                    launchactivity.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME",firstName);
                    launchactivity.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME",surName);
                    startActivity(launchactivity);

                }
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptRegisterName() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        firstNameACTV.setError(null);
        surnameET.setError(null);

        // Store values at the time of the login attempt.
        String firstName = firstNameACTV.getText().toString();
        String surname = surnameET.getText().toString();

        boolean cancel = false;
        View focusView = null;

//        // Check for a valid surname, if the user entered one.
//        if (!TextUtils.isEmpty(surname) && !isSurnameValid(surname)) {
//            surnameET.setError(getString(R.string.error_invalid_password));
//            focusView = surnameET;
//            cancel = true;
//        }
        // Check for a valid surname.
        if (TextUtils.isEmpty(surname)) {
            surnameET.setError(getString(R.string.error_field_required));
            focusView = surnameET;
            cancel = true;
        } else if (!isNameValid(surname)) {
            surnameET.setError(getString(R.string.error_invalid_field));
            focusView =surnameET;
            cancel = true;
        }

        // Check for a valid first name.
        if (TextUtils.isEmpty(firstName)) {
            firstNameACTV.setError(getString(R.string.error_field_required));
            focusView = firstNameACTV;
            cancel = true;
        } else if (!isNameValid(firstName)) {
            firstNameACTV.setError(getString(R.string.error_invalid_field));
            focusView =firstNameACTV;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            return true;

        }
        return false;
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
            Pattern number = Pattern.compile("[0-9]");
            Pattern specialChar = Pattern.compile("[!¡@€£#$¢%∞^§&¶*•(ª)º_–+≠={}<>?\\[\\]±;':.-]");
            Matcher containsNumber = number.matcher(name);
            Matcher containsSpecialChar = specialChar.matcher(name);

        return !containsNumber.find() && !containsSpecialChar.find();
        //return (!containsNumber.find() || !containsSpecialChar.find());
    }


//    private boolean isSurnameValid(String surname) {
//        //TODO: Replace this with your own logic
//        return surname.length() > 4;
//    }
}
