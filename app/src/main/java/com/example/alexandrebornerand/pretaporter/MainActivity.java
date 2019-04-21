package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexandrebornerand.pretaporter.Model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;


/***** Welcome page *****/

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MainActivity";

    private FirebaseAuth firebaseAuth;
    private CallbackManager mCallbackManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        TextView goToSignInPage = findViewById(R.id.redirect_logIn);
        goToSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(launchactivity);
            }
        });

        Button goToAccCreationPage = findViewById(R.id.redirect_createAcc);
        goToAccCreationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new Intent(getApplicationContext(), CreateAccount1Activity.class);
                startActivity(launchactivity);
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        Button buttonFacebookLogin = findViewById(R.id.redirect_FB);
        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });

            }
        });
//        buttonFacebookLogin.setReadPermissions("email", "public_profile");
//
//        /*** need to create and implement facebook button **/
//
//        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });
    }//END onCreate

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();

    }

    public void updateUI() {
        /**CHECK IF ALREADY LOGGED IN**/
        if (firebaseAuth.getCurrentUser() != null) {
            //user is already logged in. can start activity
            finish();
            startActivity(new Intent(getApplicationContext(), ListActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String facebookID = "";

                            /**** Ref: https://firebase.google.com/docs/auth/android/manage-users ****/
                            String providerId = "";
                            String uid = "";
                            String name = "";
                            String email = "";
                            Uri photoUrl = Uri.parse("");

                            for (UserInfo profile : user.getProviderData()) {
                                // Id of the provider (ex: google.com)
                                providerId = profile.getProviderId();

                                // UID specific to the provider
                                uid = profile.getUid();

                                // Name, email address, and profile photo Url
                                name = profile.getDisplayName();
                                email = profile.getEmail();
                                photoUrl = Uri.parse(profile.getPhotoUrl().toString() + "?height=500");

                                if (providerId.equals("facebook.com"))
                                    facebookID = uid;
                            }
                            //also add user to database
                            String firstName_str = "";
                            String surName_str = "";

                            String[] nameArr = name.split(" ");
                            if (!nameArr[0].isEmpty())
                                firstName_str = nameArr[0];
                            if (nameArr.length > 1) {
                                if (!nameArr[1].isEmpty())
                                    surName_str = nameArr[1];
                            }
                            String id = user.getUid();
                            String email_str = email;
                            //TODO: change once implement graph api
                            String dob = "";
                            User newUser = new User(firstName_str, surName_str, email_str, dob, id);
                            newUser.setFbID(facebookID);
                            newUser.setPhotoUrl(photoUrl.toString());
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(photoUrl).build();
                            user.updateProfile(profileUpdates);

                            databaseReference.child("users").child(id).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: added to firebase DB");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: not added bc:" + e);
                                }
                            });
                            //Navigate to explore page
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void onButtonTap(View v) {
        Toast myToast = Toast.makeText(
                getApplicationContext(),
                "Ouch!",
                Toast.LENGTH_LONG);
        myToast.show();
    }

    //ACTION BUTTON FOR: SIGN IN
    public void onTap_signIn(View v) {
        Toast signinToast = Toast.makeText(
                getApplicationContext(),
                "Signing in..!",
                Toast.LENGTH_LONG);
        signinToast.show();
    }

    //ACTION BUTTON FOR: CONNECT FACEBOOK
    public void onTap_connectFB(View v) {
        Toast signinToast = Toast.makeText(
                getApplicationContext(),
                "Connecting to FB..!",
                Toast.LENGTH_LONG);
        signinToast.show();

    }

    //ACTION BUTTON FOR CREATE ACCOUNT
    public void onTap_createAccount(View v) {
        Toast signinToast = Toast.makeText(
                getApplicationContext(),
                "Redirecting to create account..!",
                Toast.LENGTH_LONG);
        signinToast.show();
    }
}
