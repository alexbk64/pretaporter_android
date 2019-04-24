package com.example.alexandrebornerand.pretaporter;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentController;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateAccount4ActivityTest {
    @Mock
    EditText mDob;
    @Mock
    ProgressBar mProgressBar;
    @Mock
    FirebaseAuth firebaseAuth;
    @Mock
    Calendar calendar;
    @Mock
    Date dOfBirth;
    @Mock
    SimpleDateFormat simpleDateFormat;
    @Mock
    Date minimumAge;
    @Mock
    FirebaseDatabase firebaseDatabase;
    @Mock
    DatabaseReference databaseReference;
    @Mock
    User newUser;
    @Mock
    DatePickerDialog datePickerDialog;
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
    CreateAccount4Activity createAccount4Activity;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    }

    @Test
    public void testIsDobValid() {
        Date today = new Date();
        boolean output = createAccount4Activity.isDobValid("24/11/1996");
        Assert.assertEquals(true, output);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme