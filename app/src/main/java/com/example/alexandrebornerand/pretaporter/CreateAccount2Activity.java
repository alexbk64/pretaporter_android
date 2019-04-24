package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;

/***** Registration page - step 2 *****/

public class CreateAccount2Activity extends AppCompatActivity {


    //UI references
    private FirebaseAuth firebaseAuth;
    private AutoCompleteTextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email_registration);
        String infoToPass = "com.example.alexandrebornerand.pretaporter.EMAIL";

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (attemptRegister()) {
                    Snackbar.make(view, "Registering email address..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent continueToPasswordCreation = new Intent(getApplicationContext(), CreateAccount3Activity.class);
                    String email = mEmail.getText().toString();
                    String firstName = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.FIRSTNAME");
                    String surName = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.SURNAME");
                    continueToPasswordCreation.putExtra("com.example.alexandrebornerand.pretaporter.EMAIL", email);
                    continueToPasswordCreation.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME", firstName);
                    continueToPasswordCreation.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME", surName);

                    startActivity(continueToPasswordCreation);
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


        // Reset errors.
        mEmail.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
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

    boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }
}
