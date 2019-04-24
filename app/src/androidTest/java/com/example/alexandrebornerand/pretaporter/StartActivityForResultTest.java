//package com.example.alexandrebornerand.pretaporter;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.support.test.annotation.UiThreadTest;
//import android.support.test.rule.ActivityTestRule;
//import android.widget.Button;
//import junit.framework.Assert;
//
//import org.junit.Rule;
//import android.support.test.rule.ActivityTestRule;
//import android.view.View;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import static junit.framework.TestCase.assertNotNull;
//
//
//public class StartActivityForResultTest extends Activity{
//
//    @Rule
//    ActivityTestRule<addItemActivity> startActivityForResultTestActivityTestRule = new ActivityTestRule<>(StartActivityForResultTest.class, false);
//
//    //here declare and initialise activity
//
//    @Before
//    public void setUp() throws Exception {
//        this.activity = startActivityForResultTestActivityTestRule.getActivity();
//        this.startButton = (Button) this.activity.findViewById(R.id.start_button);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        this.activity.finish();
//
//        super.tearDown();
//    }
//
//    @UiThreadTest
//    public void testStartButtonOnClick() {
//        Assert.assertTrue(this.startButton.performClick());
//
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
//        Assert.assertNotNull(result);
//
//        Instrumentation.ActivityMonitor am = new Instrumentation.ActivityMonitor(ChildActivity.class.getName(), result, true);
//        Assert.assertNotNull(am);
//
//        Activity childActivity = this.getInstrumentation().waitForMonitorWithTimeout(am, TIME_OUT);
//        Assert.assertNotNull(childActivity);
//
//        Assert.fail("How do I check that StartActivityForResult correctly handles the returned result?");
//    }
//    private Activity activity = null;
//    private Button startButton = null;
//    private static final int TIME_OUT = 5 * 1000; // 5 seconds
//}