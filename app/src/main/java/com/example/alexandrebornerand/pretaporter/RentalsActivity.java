package com.example.alexandrebornerand.pretaporter;


import android.content.Context;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Adapter.ItemViewHolder;
import com.example.alexandrebornerand.pretaporter.Adapter.ViewPagerAdapter;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

//import com.squareup.picasso.Transformation;
/****MUST ACKNOWLEDGE****/
/**
 * https://developer.android.com/training/implementing-navigation/nav-drawer#java
 */

/** A DYNAMIC RECYCLERVIEW LIST ACTIVITY**/


/**V1.0 WORKING!!!**/

public class RentalsActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";



    //a list to store all the products
    private ArrayList<User> userList;
    private ArrayList<Product> productArrayList;

    //the recyclerview
    private RecyclerView recyclerView;

    //drawerLayout
    private DrawerLayout drawerLayout;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private String first_name;
    private String surname;
    private Uri photoUrl;
    private TextView mName;
    private ImageView mImg;
    private String full_name;
    private String display_name;
    private Date dob;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Product, ItemViewHolder> firebaseRecyclerAdapter;
    private String itemName;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri defaultPhoto;
    private DatabaseReference usrRef;
    private DatabaseReference listingRef;
//    private MaterialSearchView searchView;


    FirebaseRecyclerOptions<Product> options;
    private StorageReference defaultStorageRef;
    private User temp;
    private Product tempProduct;
    private String userID;



    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout relativeLayout;


//
//    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        setContentView(R.layout.drawer_layout_rentals);




//        firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        usrRef = firebaseDatabase.getReference("users/");
//        listingRef = firebaseDatabase.getReference("listings/");
        if (firebaseAuth == null) {
            //user is not logged in, redirect to welcome page
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        user = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
//
//        storageReference = firebaseStorage.getReference("/users/"+user.getUid()+"/images/");
//        defaultPhoto = Uri.parse("https://firebasestorage.googleapis.com/v0/b/pret-a-porter-46c11.appspot.com/o/users%2F4J3khWJ36aPjLFvdtoVDbjccXoz2%2Fimages%2Fdefault_profile_pic.png?alt=media&token=01e1d475-9e7d-4c7d-8f17-ed009542afe5");
//        defaultStorageRef = firebaseStorage.getReference();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
        //if (first and last) name is passed, add name to database
//        if (getIntent().hasExtra("com.example.alexandrebornerand.pretaporter.FIRSTNAME") && getIntent().hasExtra("com.example.alexandrebornerand.pretaporter.SURNAME")) {
//            first_name = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.FIRSTNAME");
//            //compile with surname;
//            String full_name = "";
//            surname = getIntent().getExtras().getString("com.example.alexandrebornerand.pretaporter.SURNAME");
//            full_name = first_name + " " + surname;
//            //add full name to database
////            Snackbar.make(mEmailView, R.string.email_exists, Snackbar.LENGTH_LONG)
////                    .setAction("Action", null).show();
//        }


        //if dob is passed, add to database


        //generate toolbar and navigation view
        genToolbarAndNavView();

        //get viewpager from activity_rentals xml
        viewPager = (ViewPager) findViewById(R.id.viewPager_rentals);
        //set up view pager
        setUpViewPager(viewPager);

        //get tab layout from xml and define TabLayout object
        tabLayout = (TabLayout) findViewById(R.id.tablayout_rentals);

        //attach viewpager to tablayout
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_done_all_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_access_time_white);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_update_white);


        relativeLayout = (RelativeLayout) findViewById(R.id.toShrink);



        //set current/active tab as default tab
        viewPager.setCurrentItem(1);



        //getting the recyclerview from xml
//        recyclerView = findViewById(R.id.recycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
//        userList = new ArrayList<>();


        /********** Material Search View listeners (ref: https://github.com/MiguelCatalan/MaterialSearchView) **********/

//        searchView = (MaterialSearchView) findViewById(R.id.search_view);

//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                tabLayout.setVisibility(View.GONE);
//
//                //Do some magic
//                firebaseSearch(query);
//                //TODO: figure out another way to hide softKeyboard
//                closeKeyboard();//doesn't work because its not called from an activity.. thanks @Android
////                Toast.makeText(getApplicationContext(),"Our word : "+query,Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                tabLayout.setVisibility(View.GONE);
//
//                //filter as user types
//                firebaseSearch(newText);
//                return false;
//            }
//        });

//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//
//            @Override
//            public void onSearchViewShown() {
////                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
////                lp.topMargin -= 200;
////                tabLayout.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//                firebaseSearch("");
////                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
////                lp.topMargin += 200;
////                tabLayout.setVisibility(View.VISIBLE);
//
//
//            }
//        });
    }//END onCreate

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments
        viewPagerAdapter.addFrag(new FragmentPastRentals(), "Past");
        viewPagerAdapter.addFrag(new FragmentActiveRentals(), "Active");
        viewPagerAdapter.addFrag(new FragmentUpComingRentals(), "Upcoming");

        viewPager.setAdapter(viewPagerAdapter);
    }

//    //firebase search function
//    private void firebaseSearch(String searchText) {
//        final ProgressBar progressBar = new ProgressBar(RentalsActivity.this, null, android.R.attr.progressBarStyleSmall);
//        progressBar.setVisibility(View.VISIBLE);
////
////        Query searchQuery = databaseReference
////                .child("rentals")
////                .orderByChild("name")
////                .startAt(searchText)
////                .endAt(searchText + "\uf8ff");
////
////        FirebaseRecyclerOptions<Product> options =
////                new FirebaseRecyclerOptions.Builder<Product>()
////                        .setQuery(searchQuery, Product.class)
////                        .setLifecycleOwner(this)
////                        .build();
////
////        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ItemViewHolder>(options) {
////            @Override
////            protected void onBindViewHolder(final ItemViewHolder viewHolder, final int position, Product model) {
////                Log.d(TAG, "onBindViewHolder: called.");
////                // Bind the Chat object to the ChatHolder
////                // ...
////                //if (productList_perso.size() > 0) {
//////                    Product item = productList_perso.get(position);
//////                    ItemViewHolder holder = (ItemViewHolder) viewHolder;
////                //holder.image.setImageResource(ImageView.item.get_image_main());
////
////                viewHolder.name.setText(model.getName());
////                viewHolder.short_description.setText(model.getDescription());
////                viewHolder.rating.setText(String.valueOf(model.getRating()));
////                String temp = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(model.getDaily_fee());
////                viewHolder.price.setText(temp);
//////                String listingID = userList.get(position).getId();
////
//////                String userID = userList.get(position).getId();
////
//////                String userID = productArrayList.get(position).getLister().getId();
////
//////                String listingID = model.getId();
//////                String userID = model.getLister().getId();
////                String key = getRef(position).getKey();
////                String listingID = key;
////                userID="";
////                for (int i=0; i<productArrayList.size(); i++) {
////                    if (productArrayList.get(i).getId().equals(key)){
////                        userID = productArrayList.get(i).getLister().getId();
////                    }
////                }
////
////                if (key!=null) {
////
//////                    StorageReference currentStorageReference = storageReference.child(key);
////                    StorageReference currentStorageReference = defaultStorageRef.child("/users/"+userID+"/images/"+listingID).child("0");
////                    currentStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                        @Override
////                        public void onSuccess(Uri uri) {
//////                            Glide.with(getApplicationContext())
//////                                    .load(uri)
//////                                    .into(viewHolder.image);
////                            //photoUrl = uri;
////                            viewHolder.setImage(uri, viewHolder.image);
////                            Log.d(TAG, "onBindViewHolder: photoUri = " + uri);
////                        }
////                    }).addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////                            //do nothing
////                            //photoUrl=null;
////                            Log.w(TAG, "onFailure: failed to get downloadUri", e);
////                        }
////                    });
////
////
////                    // viewHolder.setImage(photoUrl.toString(), viewHolder.image);
////                }
////                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Log.d(TAG, "onClick: clicked on: ");
////                        String visit_product_page = getRef(position).getKey();
//////                        String lister = userID;
//////                        Intent productPageIntent = new Intent(getApplicationContext(), ProductPageActivityAlt.class);
////                        Intent productPageIntent = new Intent(getApplicationContext(), ProductPageActivity.class);
////                        productPageIntent.putExtra("visit_product_page", visit_product_page);
//////                        productPageIntent.putExtra("lister", userID);
////                        startActivity(productPageIntent);
////                    }
////                });
////            }
////            @NonNull
////            @Override
////            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////                // Create a new instance of the ViewHolder, in this case we are using a custom
////                // layout called R.layout.message for each item
////                View view = LayoutInflater.from(getBaseContext())
////                        .inflate(R.layout.card_product_layout, parent, false);
////
////                return new ItemViewHolder(view);
////            }
////        };
////        //        //setting adapter to recyclerview
//////        firebaseRecyclerAdapter.startListening();
////        recyclerView.setAdapter(firebaseRecyclerAdapter);
//////        firebaseRecyclerAdapter.notifyDataSetChanged();
//    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }//END onStart()

    public void genToolbarAndNavView() {

        /** TOOLBAR IMPLEMENTATION **/

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
//        else if (mActionBar == null)
//            System.out.println("actionbar is null");


        /**NAVIGATION DRAWER IMPLEMENTATION**/
        //implement OnNavigationItemSelectedListener interface and attach it to NavigationView by
        //calling setNavigationItemSelectedListener()
        navigationView = findViewById(R.id.nav_view_rentals);
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
                        //if user clicked settings
                        else if (menuItem.getItemId() == R.id.nav_settings) {
                            //TODO: uncomment below once toolbar implemented on settings page
                            //finish();
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
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
//    /********** Material Search View (ref: https://github.com/MiguelCatalan/MaterialSearchView) **********/
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        return true;
//    }
//    /********** Material Search View (ref: https://github.com/MiguelCatalan/MaterialSearchView) **********/
//    @Override
//    public void onBackPressed() {
//        if (searchView.isSearchOpen()) {
//            searchView.closeSearch();
////            tabLayout.setVisibility(View.VISIBLE);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Open navigation drawer when menu button is pressed
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
                //hide tabLayout when user clicks on search. Whatsapp-like search-bar animation.
//            case R.id.action_search:
//                tabLayout.setVisibility(View.GONE);
//                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    /***** method to get user info  *******/

    private void getUserInfo() {
        //double check user exists to avoid errors
        if (user != null) {
//            for (UserInfo profile : user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address, and profile photo Url
//                name = profile.getDisplayName();
//                photoUrl = profile.getPhotoUrl();
//                mName.setText(name);
//
//                Picasso.with(getApplicationContext())
//                        .load(photoUrl.toString())
//                        .placeholder(R.drawable.logo)
//                        .resize(100, 100)
//                        .transform(new CircleTransform())
//                        .centerCrop()
//                        .into(mPic);
//            }
            // Name, email address, and profile photo Url
            String display_name_hdr = user.getDisplayName();
            Uri photoUrl_hdr = user.getPhotoUrl();
            mName.setText(display_name_hdr);
            StorageReference temp = FirebaseStorage.getInstance().getReference("/users/"+user.getUid()+"/profilePicture");
//            temp.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
//                @Override
//                //if fails, file doesnt exist, so set profile pic to photoUrl. otherwise profile pic should be what user
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            })
            Glide.with(getApplicationContext())
//                    .load(temp)
                    .load(photoUrl_hdr)
                    .error(R.drawable.default_profile_photo_circle)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImg);

        }//END if user is not null
    }//END method getUserInfo()

}//END class