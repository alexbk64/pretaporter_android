package com.example.alexandrebornerand.pretaporter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void isEmailValid() {
        String input = "john@smith.com";
        boolean output;
        boolean expected = true;

        LoginActivity loginActivity = new LoginActivity();
        output = loginActivity.isEmailValid(input);

        //important part, assert method
        assertEquals(expected, output);
    }

    @Test
    public void isPasswordValid() {
        String password1 = "helloworld";
        String password2 = "Helloworld";
        String password3 = "Helloworld1";
        String password4 = "Helloworld1!";

        String input = password4;
        boolean output;
        boolean expected=true;

        LoginActivity loginActivity = new LoginActivity();
        output = loginActivity.isPasswordValid(input);

        //important part, assert method
        assertEquals(expected, output);

    }

    @Test
    public void onTap_signIn() {
    }

    @Test
    public void onTap_forgotPass() {
    }
}