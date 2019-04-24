package com.example.alexandrebornerand.pretaporter;

import org.junit.Assert;
import org.junit.Test;

public class LoginActivityTest {


    @Test
    public void isEmailValid() {
        //invalid
        String email1 = "helloworld";
        //invalid
        String email2 = "helloworld.com";
        //invalid
        String email3 = "hellow@world";
        //valid
        String email4 = "hello@world.com";
        boolean result1 = LoginActivity.isEmailValid(email1);
        boolean result2 = LoginActivity.isEmailValid(email2);
        boolean result3 = LoginActivity.isEmailValid(email3);
        boolean result4 = LoginActivity.isEmailValid(email4);
        Assert.assertEquals(false, result1);
        Assert.assertEquals(false, result2);
        Assert.assertEquals(false, result3);
        Assert.assertEquals(true, result4);
    }

    @Test
    public void isPasswordValid() {
        //invalid
        String password1 = "helloworld";
        //invalid
        String password2 = "Helloworld";
        //invalid
        String password3 = "Helloworld1";
        //valid
        String password4 = "Helloworld1!";
        boolean result1 = LoginActivity.isPasswordValid(password1);
        boolean result2 = LoginActivity.isPasswordValid(password2);
        boolean result3 = LoginActivity.isPasswordValid(password3);
        boolean result4 = LoginActivity.isPasswordValid(password4);
        Assert.assertEquals(false, result1);
        Assert.assertEquals(false, result2);
        Assert.assertEquals(false, result3);
        Assert.assertEquals(true, result4);

    }

    @Test
    public void onTap_signIn() {
    }
}