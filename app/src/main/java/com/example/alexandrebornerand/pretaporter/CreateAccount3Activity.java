package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/***** Registration page - step 3 *****/

public class CreateAccount3Activity extends AppCompatActivity {
    //TODO: Add option to show/hide password
    //UI references
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPassword = findViewById(R.id.passWordET);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptRegister()) {
                    Snackbar.make(view, "Registering password..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent continueToDobActivity = new Intent(getApplicationContext(), CreateAccount4Activity.class);


                    String password = mPassword.getText().toString();
                    String email = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.EMAIL");
                    String firstName = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.FIRSTNAME");
                    String surName = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.SURNAME");
                    continueToDobActivity.putExtra("com.example.alexandrebornerand.pretaporter.EMAIL", email);
                    continueToDobActivity.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME", firstName);
                    continueToDobActivity.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME", surName);
                    continueToDobActivity.putExtra("com.example.alexandrebornerand.pretaporter.PASSWORD", password);

                    startActivity(continueToDobActivity);
                }
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptRegister() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password address.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!LoginActivity.isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            return true;

        }
    }

}
