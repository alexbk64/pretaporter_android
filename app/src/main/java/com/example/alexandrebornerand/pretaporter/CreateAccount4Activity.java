package com.example.alexandrebornerand.pretaporter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***** Registration page - step 4 *****/

public class CreateAccount4Activity extends AppCompatActivity {
    //TODO: DOB needs updating. currently not uploading to db
    private EditText mDob;
    private ProgressBar mProgressBar;
    private FirebaseAuth firebaseAuth;
    private String email_str;
    private String password_str;
    private String firstName_str;
    private String surName_str;
    private Calendar calendar;
    private Date dOfBirth;
    private SimpleDateFormat simpleDateFormat;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User newUser;
    String id;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialisations

        firebaseAuth = FirebaseAuth.getInstance();
        /**CHECK IF ALREADY LOGGED IN**/
        if (firebaseAuth.getCurrentUser() != null) {
            //user is already logged in. can start activity
            finish();
            startActivity(new Intent(getApplicationContext(), ListActivity.class));
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        mDob = findViewById(R.id.dobET);
        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(CreateAccount4Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        Date date = cal.getTime();
                        mDob.setText(simpleDateFormat.format(date));
                    }
                }, y, m, d);
                DatePicker dp = datePickerDialog.getDatePicker();
                //sets min date to today's date minus 100 years, sets max date to today - 18 years,
                calendar.add(Calendar.YEAR, -18);
                dp.setMaxDate(calendar.getTimeInMillis());
                calendar.add(Calendar.YEAR, -82);
                dp.setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        String dob = mDob.getText().toString();


        //set date format
        //MM-dd-yyyy, MM.dd.yyyy, dd.MM.yyyy, etc.
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        //create Date object
        //parse string into Date
        try {
            dOfBirth = simpleDateFormat.parse(dob);
        }
        //invalid
        catch (ParseException e) {
            //invalid
            e.printStackTrace();

        }


        //long millis = date.getTime();
        mProgressBar = findViewById(R.id.login_progress);


        password_str = getIntent().getExtras().getString(("com.example.alexandrebornerand.pretaporter.PASSWORD"));
        email_str = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.EMAIL");
        firstName_str = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.FIRSTNAME");
        surName_str = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.SURNAME");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();

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
        mDob.setError(null);

        // Store values at the time of the login attempt.
        final String dob = mDob.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid dob.
        if (TextUtils.isEmpty(dob)) {
            mDob.setError(getString(R.string.error_field_required));
            focusView = mDob;
            cancel = true;
        } else if (!isDobValid(dob)) {
            mDob.setError(getString(R.string.error_invalid_date));
            focusView = mDob;
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
            //mProgressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email_str, password_str)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            /****** SUCCESSFUL REGISTRATION ***/
                            if (task.isSuccessful()) {
                                //user is successfully registered and logged in.
                                String display_name = firstName_str + " " + surName_str;
                                //Inform user of registration success
                                Toast.makeText(getApplicationContext(), "Registration successful: " + display_name, Toast.LENGTH_SHORT).show();
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(display_name).build();
                                user.updateProfile(profileUpdates);
                                //also add user to Realtime Database
                                id = user.getUid();
                                newUser = new User(firstName_str, surName_str, email_str, dob, id);
                                databaseReference.child("users").child(id).setValue(newUser);
                                //End the current activity and navigate to the Explore page
                                finish();
                                startActivity(new Intent(getApplicationContext(), ListActivity.class));
                            }
                        }
                    });
            return true;
        }
    }

    boolean isDobValid(String dob) {
        //check if null
        if (dob.trim().equals(""))
            return false;
        else {

            //create Date object
            //parse string into Date
            try {
                Date javaDob = simpleDateFormat.parse(dob);
                //valid
            }
            //invalid
            catch (ParseException e) {
                //invalid
                return false;
            }
            //return true if valid
            return true;
        }
    }

}
