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
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class CreateAccount1ActivityTest {
    @Mock
    AutoCompleteTextView firstNameACTV;
    @Mock
    EditText surnameET;
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
    CreateAccount1Activity createAccount1Activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testIsNameValid() throws Exception {
        String valid_name = "name";
        String invalid_name = "n4me";
        boolean resultValid = createAccount1Activity.isNameValid(valid_name);
        assertEquals(true, resultValid);
        boolean resultInvalid = createAccount1Activity.isNameValid(invalid_name);
        assertEquals(false, resultInvalid);

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme