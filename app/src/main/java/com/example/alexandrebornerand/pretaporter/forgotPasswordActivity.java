package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    //UI references
    private FirebaseAuth firebaseAuth;
    private AutoCompleteTextView mEmail;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email_registration);
        //final String email = mEmail.getText().toString();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmail()) {
                    Snackbar.make(view, "Sending email..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent continueToLogIn = new Intent(getApplicationContext(), LoginActivity.class);
                    String email = mEmail.getText().toString();

                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        success = true;

                                    } else if (!task.isSuccessful())
                                        success = false;
                                }
                            });
//
                    if (success = true) {
                        Toast forgottenToast = Toast.makeText(
                                getApplicationContext(),
                                "Email sent!",
                                Toast.LENGTH_LONG);
                        forgottenToast.show();
                        startActivity(continueToLogIn);
                    } else {
                        Toast forgottenToast = Toast.makeText(
                                getApplicationContext(),
                                "email NOT sent!",
                                Toast.LENGTH_LONG);
                        forgottenToast.show();
                    }

                }
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    boolean checkEmail() {
//        if (mAuthTask != null) {
//            return;
//        }

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
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }
}
