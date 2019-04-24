package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertNotNull;

public class launchActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<addItemActivity> addItemActivityTestRule = new ActivityTestRule<>(addItemActivity.class);
    @Rule
    public ActivityTestRule<CreateAccount1Activity> createAccount1ActivityTestRule = new ActivityTestRule<>(CreateAccount1Activity.class);
    @Rule
    public ActivityTestRule<CreateAccount2Activity> createAccount2ActivityTestRule = new ActivityTestRule<>(CreateAccount2Activity.class, true, false);
    @Rule
    public ActivityTestRule<CreateAccount3Activity> createAccount3ActivityTestRule = new ActivityTestRule<>(CreateAccount3Activity.class, true, false);
    @Rule
    public ActivityTestRule<CreateAccount4Activity> createAccount4ActivityTestRule = new ActivityTestRule<>(CreateAccount4Activity.class, true, false);
    @Rule
    public ActivityTestRule<dateSelectorActivity> dateSelectorActivityActivityTestRule = new ActivityTestRule<>(dateSelectorActivity.class, true, false);
    @Rule
    public ActivityTestRule<editItemActivity> editItemActivityActivityTestRule = new ActivityTestRule<>(editItemActivity.class, true, false);
    @Rule
    public ActivityTestRule<forgotPasswordActivity> forgotPasswordActivityActivityTestRule = new ActivityTestRule<>(forgotPasswordActivity.class);
    @Rule
    public ActivityTestRule<ListActivity> listActivityActivityTestRule = new ActivityTestRule<>(ListActivity.class);
    @Rule
    public ActivityTestRule<UserListingsActivity> userListingsActivityActivityTestRule = new ActivityTestRule<>(UserListingsActivity.class);
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<ProductPageActivity> productPageActivityActivityTestRule = new ActivityTestRule<>(ProductPageActivity.class, true, false);
    @Rule
    public ActivityTestRule<ProfilePageActivity> profilePageActivityActivityTestRule = new ActivityTestRule<>(ProfilePageActivity.class);
    @Rule
    public ActivityTestRule<RentalsActivity> rentalsActivityActivityTestRule = new ActivityTestRule<>(RentalsActivity.class);



    //create dummy user
    private User user = new User("firstName", "surname", "email@example.com", "dd/MM/yyyy", "id");
    //dummy String arraylist
    private ArrayList<String> stringArrayList = new ArrayList<>();
    //create dummy product
    private Product product = new Product("id", "name", "description", (float) 66.0, 5.0, stringArrayList, user, "category", "size", "colour");
//
//
    private addItemActivity addItemActivity = null;
    private CreateAccount1Activity createAccount1Activity = null;
    private CreateAccount2Activity createAccount2Activity = null;
    private CreateAccount3Activity createAccount3Activity = null;
    private CreateAccount4Activity createAccount4Activity = null;
    private dateSelectorActivity dateSelectorActivity = null;
    private editItemActivity editItemActivity = null;
    //forgot password
    private forgotPasswordActivity forgotPasswordActivity = null;
    //list activity
    private ListActivity listActivity = null;
    //login activity
    private LoginActivity loginActivity = null;
    //main activity
    private MainActivity mainActivity = null;
    //product page activity
    private ProductPageActivity productPageActivity = null;
    //profile page activity
    private ProfilePageActivity profilePageActivity = null;
    //rentals activity
    private RentalsActivity rentalsActivity = null;
    //user listings
    private UserListingsActivity userListingsActivity = null;

    @Before
    public void setUp() throws Exception {
        //redundant to initialise activities which are not yet launched here
        addItemActivity = addItemActivityTestRule.getActivity();
        createAccount1Activity = createAccount1ActivityTestRule.getActivity();
        editItemActivity = editItemActivityActivityTestRule.getActivity();
        dateSelectorActivity = dateSelectorActivityActivityTestRule.getActivity();
        //forgot password
        forgotPasswordActivity = forgotPasswordActivityActivityTestRule.getActivity();
        //list activity
        listActivity = listActivityActivityTestRule.getActivity();
        //login activity
        loginActivity = loginActivityActivityTestRule.getActivity();
        mainActivity = mainActivityActivityTestRule.getActivity();
        productPageActivity = productPageActivityActivityTestRule.getActivity();
        profilePageActivity = profilePageActivityActivityTestRule.getActivity();
        rentalsActivity = rentalsActivityActivityTestRule.getActivity();
        userListingsActivity = userListingsActivityActivityTestRule.getActivity();
//

    }

    @After
    public void tearDown() throws Exception {
        addItemActivity = null;
        createAccount1Activity = null;
        createAccount2Activity = null;
        createAccount3Activity = null;
        createAccount4Activity = null;
        dateSelectorActivity = null;
        editItemActivity = null;
        //forgot password activity
        forgotPasswordActivity = null;
        loginActivity = null;
        listActivity = null;
        mainActivity = null;
        productPageActivity = null;
        profilePageActivity = null;
        rentalsActivity = null;
        userListingsActivity = null;

    }

    //addItem Activity
    //test launch addItemActivity
    @Test
    public void testLaunchAddItemActivity() {
//        //try to find view by id. If found, then activity must be launched.
        View view= addItemActivity.findViewById(R.id.lin_layout_addItemActivity);
        assertNotNull(view);
    }
    //test launch createAccount1Activity
    @Test
    public void testLaunchCreateAccount1Activity() {
        //try to find view by id. If found, then activity must be launched.
        View view= createAccount1Activity.findViewById(R.id.fab);
        assertNotNull(view);
    }
    //test launch createAccount2Activity
    @Test
    public void testLaunchCreateAccount2Activity() {
        //create intent
        Intent intent = new Intent();
        intent.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME", "firstName");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME", "surname");
        createAccount2ActivityTestRule.launchActivity(intent);
        //set up here do due delayed launch of activity
        createAccount2Activity = createAccount2ActivityTestRule.getActivity();
//        onView(withId(R.id.email_TextView)).check(matches(withText("email address")));
        //try to find view by id. If found, then activity must be launched.
        View view= createAccount2Activity.findViewById(R.id.fab);
        assertNotNull(view);
    }
    //test launch createAccount2Activity
    @Test
    public void testLaunchCreateAccount3Activity() {
        //create intent
        Intent intent = new Intent();
        intent.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME", "firstName");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME", "surname");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.EMAIL", "email");
        createAccount3ActivityTestRule.launchActivity(intent);
        //set up here do due delayed launch of activity
        createAccount3Activity = createAccount3ActivityTestRule.getActivity();
//        onView(withId(R.id.email_TextView)).check(matches(withText("email address")));
        //try to find view by id. If found, then activity must be launched.
        View view= createAccount3Activity.findViewById(R.id.fab);
        assertNotNull(view);
    }
    //test launch of createAccount4Activity
    @Test
    public void testLaunchCreateAccount4Activity() {
        Intent intent = new Intent();
        intent.putExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME", "firstName");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.SURNAME", "surname");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.EMAIL", "email");
        intent.putExtra("com.example.alexandrebornerand.pretaporter.PASSWORD", "password");
        createAccount4ActivityTestRule.launchActivity(intent);
        //set up here do due delayed launch of activity
        createAccount4Activity = createAccount4ActivityTestRule.getActivity();
//        onView(withId(R.id.email_TextView)).check(matches(withText("email address")));
        //try to find view by id. If found, then activity must be launched.
        View view= createAccount4Activity.findViewById(R.id.fab);
        assertNotNull(view);
    }
    @Test
    public void testLaunchEditItemActivity() {
        Intent intent = new Intent();
        intent.putExtra("product", product);
        editItemActivityActivityTestRule.launchActivity(intent);
        editItemActivity = editItemActivityActivityTestRule.getActivity();
        //try to find view
        View view = editItemActivity.findViewById(R.id.title_EditText);
        assertNotNull(view);

    }
    //test launch of dateSelectorActivity
    @Test
    public void testLaunchDateSelectorActivity() {
        Intent intent = new Intent();
        intent.putExtra("product", product);
        dateSelectorActivityActivityTestRule.launchActivity(intent);
        dateSelectorActivity = dateSelectorActivityActivityTestRule.getActivity();
        View view = dateSelectorActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }
    @Test
    public void testLaunchForgotPasswordActivity() {
        View view = forgotPasswordActivity.findViewById(R.id.email_registration);
        assertNotNull(view);
    }
    //test launch of list activity
    @Test
    public void testLaunchListActivity() {
        //try to find view
        View view = listActivity.findViewById(R.id.recycler);
        assertNotNull(view);
    }
    //product page
    @Test
    public void testLaunchProductPageActivity() {
        Intent intent = new Intent();
        intent.putExtra("visit_product_page", product.getId());
        intent.putExtra("filter", "filter");
        productPageActivityActivityTestRule.launchActivity(intent);
        productPageActivity = productPageActivityActivityTestRule.getActivity();
        View view = productPageActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }
    //test something else on product page



}