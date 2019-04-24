package com.example.alexandrebornerand.pretaporter;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentController;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.AutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateAccount2ActivityTest {
    @Mock
    FirebaseAuth firebaseAuth;
    @Mock
    AutoCompleteTextView mEmail;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    Handler mHandler;
    @Mock
    FragmentController mFragments;
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    SimpleArrayMap<Class<? extends SupportActivity.ExtraData>, SupportActivity.ExtraData> mExtraDataMap;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    @InjectMocks
    CreateAccount2Activity createAccount2Activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testIsEmailValid() throws Exception {
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
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme