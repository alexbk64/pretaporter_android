package com.example.alexandrebornerand.pretaporter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***** Sign-in page *****/

public class LoginActivity extends AppCompatActivity {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:HelloWorld1!", "bar@example.com:WorldHello1!"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Context mContext;
    private TextView forgotPasswordBtn;
    //private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.drawer_layout_listings);

        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        //populateAutoComplete();
        mPasswordView = findViewById(R.id.password);
        //mProgressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);

        mContext = getApplicationContext();
        forgotPasswordBtn = findViewById(R.id.forgotPassTV);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast forgottenToast = Toast.makeText(
                        getApplicationContext(),
                        "Forgotten Password..!",
                        Toast.LENGTH_LONG);
                forgottenToast.show();
                startActivity(new Intent(getApplicationContext(), forgotPasswordActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();


        /**CHECK IF ALREADY LOGGED IN**/
        if (firebaseAuth.getCurrentUser() != null) {
            //user is already logged in. can start activity
            finish();
            startActivity(new Intent(mContext, ListActivity.class));

        }
//        //Handles case of email already existing. Pre-fills form and notifies user
//        if (getIntent().hasExtra("com.example.alexandrebornerand.pretaporter.EMAIL")) {
//            String emailAddress = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.EMAIL");
//            mEmailView.setText(emailAddress);
//            Snackbar.make(mEmailView, R.string.email_exists, Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.signInBtn);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                //if (mAuthTask!=null)
                if (firebaseAuth != null)
                    onTap_signIn(view);
            }
        });

        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        Context context = mEmailView.getContext();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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
            //mAuthTask = new UserLoginTask(email, password, context);
            // mAuthTask.execute((Void) null);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), ListActivity.class));
                            } else {
                                mPasswordView.setError(getString(R.string.error_incorrect_password));
                                mPasswordView.requestFocus();
                            }
                        }
                    });
        }
    }

    //check if email is valid
    boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    //check if password is valid
    public static boolean isPasswordValid(String password) {
        if (password.length() >= 8) {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern number = Pattern.compile("[0-9]");
            Pattern specialChar = Pattern.compile("[!¡@€£#$¢%∞^§&¶*•(ª)º_–+≠={}/<>?\\[\\]±;':.-]");

            Matcher containsLetter = letter.matcher(password);
            Matcher containsNumber = number.matcher(password);
            Matcher containsSpecialChar = specialChar.matcher(password);

            return containsLetter.find() && containsNumber.find() && containsSpecialChar.find();
        } else
            return false;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

    //ACTION BUTTON FOR: SIGN IN
    public void onTap_signIn(View v) {
        Toast signinToast = Toast.makeText(
                getApplicationContext(),
                "Signing in..!",
                Toast.LENGTH_LONG);
        signinToast.show();
    }

    //ACTION BUTTON FOR: FORGOT PASSWORD
    public void onTap_forgotPass(View v) {


        /** DO SOMETHING - FORGOTTEN PASS **/

    }

//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        Context mContext;
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password, Context context) {
//            mEmail = email;
//            mPassword = password;
//            mContext = context.getApplicationContext();
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            //showProgress(false);
//
//            if (success) {
//                super.onPostExecute(success);
//                //LoginActivity.ProgressBar.dismiss();
//                mContext.startActivity(new Intent(mContext, ListActivity.class));
////                Intent launchactivity = new Intent(getApplicationContext(), ScrollingMainActivity.class);
////                getApplicationContext().startActivity(launchactivity);
//                //test
//                //finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            //showProgress(false);
//        }
//    }

}
