package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.squareup.timessquare.CalendarPickerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertNotNull;

public class dateSelectorActivityIntegrationTest {

    @Rule
    public ActivityTestRule<dateSelectorActivity> dateSelectorActivityActivityTestRule = new ActivityTestRule<>(dateSelectorActivity.class, true, false);

    //declare and initalise
    private dateSelectorActivity dateSelectorActivity = null;

    //create dummy user
    private User user = new User("firstName", "surname", "email@example.com", "dd/MM/yyyy", "id");
    //dummy String arraylist
    private ArrayList<String> stringArrayList = new ArrayList<>();
    //create dummy product
    private Product product = new Product("id", "name", "description", (float) 66.0, 5.0, stringArrayList, user, "category", "size", "colour");

    //date pickr
    CalendarPickerView datePicker;
    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("product", product);
        dateSelectorActivityActivityTestRule.launchActivity(intent);
        dateSelectorActivity = dateSelectorActivityActivityTestRule.getActivity();
        datePicker = (CalendarPickerView) dateSelectorActivity.findViewById(R.id.calendar_view);

    }

    @After
    public void tearDown() throws Exception {
        dateSelectorActivity = null;
    }

    @Test
    public void testLaunchDateSelectorActivity() {
        View view = dateSelectorActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }
    @Test
    public void testDatePicker() {
        assertNotNull(datePicker);
    }

}