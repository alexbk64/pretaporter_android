package com.example.alexandrebornerand.pretaporter;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Adapter.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/****MUST ACKNOWLEDGE****/
/**
 * https://developer.android.com/training/implementing-navigation/nav-drawer#java
 */

/** A DYNAMIC RECYCLERVIEW LIST ACTIVITY**/


/**V1.0 WORKING!!!**/

public class RentalsActivity extends AppCompatActivity {

    private static final String TAG = "RentalsActivity";



    //drawerLayout
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    private TextView mName;
    private ImageView mImg;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_rentals);


//        firebase
        firebaseAuth = FirebaseAuth.getInstance();
//        listingRef = firebaseDatabase.getReference("listings/");
        if (firebaseAuth == null) {
            //user is not logged in, redirect to welcome page
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();

        //generate toolbar and navigation view
        genToolbarAndNavView();

        //get viewpager from activity_rentals xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_rentals);
        //set up view pager
        setUpViewPager(viewPager);

        //get tab layout from xml and define TabLayout object
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_rentals);

        //attach viewpager to tablayout
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_done_all_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_access_time_white);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_update_white);



        //set current/active tab as default tab
        viewPager.setCurrentItem(1);


    }//END onCreate

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments
        viewPagerAdapter.addFrag(new FragmentPastRentals(), "Past");
        viewPagerAdapter.addFrag(new FragmentActiveRentals(), "Active");
        viewPagerAdapter.addFrag(new FragmentUpComingRentals(), "Upcoming");

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }//END onStart()

    public void genToolbarAndNavView() {

        /** TOOLBAR IMPLEMENTATION **/

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        /**NAVIGATION DRAWER IMPLEMENTATION**/
        //implement OnNavigationItemSelectedListener interface and attach it to NavigationView by
        //calling setNavigationItemSelectedListener()
        NavigationView navigationView = findViewById(R.id.nav_view_rentals);
        mName = navigationView.getHeaderView(0).findViewById(R.id.nav_hdr_txtView);
        mImg = navigationView.getHeaderView(0).findViewById(R.id.nav_hdr_pPic);
        getUserInfo();
        drawerLayout = findViewById(R.id.drawer_layout_rentals);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //set item as selected to persist highlight
                        menuItem.setChecked(true);
                        //close drawer when item is touched
                        drawerLayout.closeDrawers();

                        //if user clicked log out
                        if (menuItem.getItemId() == R.id.nav_log_out) {
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        //if user clicked profile
                        else if (menuItem.getItemId() == R.id.nav_profile) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class));
                        }

                        //if user clicked explore
                        else if (menuItem.getItemId() == R.id.nav_explore) {
                            //TODO: what to do when click Explore
                            finish();
                            startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        }
                        //if user clicked my listings
                        else if (menuItem.getItemId() == R.id.nav_user_listings) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserListingsActivity.class));
                        }
                        //if user clicked rentals
                        else if (menuItem.getItemId() == R.id.nav_rentals) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), RentalsActivity.class));
                        }
                        return true;
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Open navigation drawer when menu button is pressed
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /***** method to get user info  *******/

    private void getUserInfo() {
        //double check user exists to avoid errors
        if (user != null) {
            // Name, email address, and profile photo Url
            String display_name_hdr = user.getDisplayName();
            Uri photoUrl_hdr = user.getPhotoUrl();
            mName.setText(display_name_hdr);
            StorageReference temp = FirebaseStorage.getInstance().getReference("/users/"+user.getUid()+"/profilePicture");

            Glide.with(getApplicationContext())
                    .load(photoUrl_hdr)
                    .error(R.drawable.default_profile_photo_circle)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImg);
        }//END if user is not null
    }//END method getUserInfo()

}//END class