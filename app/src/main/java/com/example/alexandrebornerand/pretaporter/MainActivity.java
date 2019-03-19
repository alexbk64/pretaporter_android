package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        /**CHECK IF ALREADY LOGGED IN**/
        if (firebaseAuth.getCurrentUser() != null) {
            //user is already logged in. can start activity
            finish();
            startActivity(new Intent(getApplicationContext(), ListActivity.class));
        }

        Button goToSignInPage = (Button) findViewById(R.id.redirect_logIn);
        goToSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new Intent(getApplicationContext(), Login1Activity.class);
                startActivity(launchactivity);
            }
        });

        Button goToAccCreationPage = (Button) findViewById(R.id.redirect_createAcc);
        goToAccCreationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new Intent(getApplicationContext(), CreateAccount1Activity.class);
                startActivity(launchactivity);
            }
        });

//        Button buttonFacebookLogin = findViewById(R.id.redirect_FB);
//        buttonFacebookLogin.setReadPermissions("email", "public_profile");

        /*** need to create and implement facebook button **/
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
