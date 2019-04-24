package com.example.alexandrebornerand.pretaporter;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class ProductPageActivityTest {
    @Rule
    public ActivityTestRule<ProductPageActivity> productPageActivityActivityTestRule = new ActivityTestRule<>(ProductPageActivity.class, true, false);

    private ProductPageActivity productPageActivity;

    //create dummy user
    private User user = new User("firstName", "surname", "email@example.com", "dd/MM/yyyy", "id");
    //dummy String arraylist
    private ArrayList<String> stringArrayList = new ArrayList<>();
    //create dummy product
    private Product product = new Product("id", "name", "description", (float) 66.0, 5.0, stringArrayList, user, "category", "size", "colour");
    private Button checkAvailability;
    private static final int TIME_OUT = 5*1000; //5 seconds

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("visit_product_page", product.getId());
        intent.putExtra("filter", "filter");
        productPageActivityActivityTestRule.launchActivity(intent);
        productPageActivity = productPageActivityActivityTestRule.getActivity();
        this.checkAvailability = (Button) productPageActivity.findViewById(R.id.availability_btn);
    }
    @After
    public void tearDown() throws Exception {
        productPageActivity = null;
        checkAvailability = null;
    }
    @Test
    public void testLaunch() {
        //test launch
        View view = productPageActivity.findViewById(R.id.toolbar);
        assertNotNull(view);

    }

    @UiThreadTest
    public void testStartActivityForResult() {
        //test launch
        assertTrue(checkAvailability.performClick());
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        Instrumentation.ActivityMonitor activityMonitor = new Instrumentation.ActivityMonitor(dateSelectorActivity.class.getName(), activityResult, true);
        Activity dateSelectorActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, TIME_OUT);
        assertNotNull(dateSelectorActivity);
    }
}