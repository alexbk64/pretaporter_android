package com.example.alexandrebornerand.pretaporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.alexandrebornerand.pretaporter.Adapter.MyAdapter;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
/****MUST ACKNOWLEDGE****/
/**
 * https://developer.android.com/training/implementing-navigation/nav-drawer#java
 */

/** A DYNAMIC RECYCLERVIEW LIST ACTIVITY**/

public class ListActivity extends AppCompatActivity {

    //Database helper
    DatabaseHelper productDB;


    MyAdapter adapter;
    //a list to store all the products
    private List<Product> productList;

    //the recyclerview
    private RecyclerView recyclerView;

    //drawerLayout
    private DrawerLayout drawerLayout;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private FirebaseAuth firebaseAuth;
//    private String first_name;
//    private String surname;
//    private Uri uri;
//    private ImageView img;
//
//    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        setContentView(R.layout.drawer_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth == null) {
            //user is not logged in, redirect to welcome page
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

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



        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar= getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_more);
        }
//        else if (mActionBar == null)
//            System.out.println("actionbar is null");


        //implement OnNavigationItemSelectedListener interface and attach it to NavigationView by
        //calling setNavigationItemSelectedListener()
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //set item as selected to persist highlight
                        menuItem.setChecked(true);
                        //close drawer when item is touched
                        drawerLayout.closeDrawers();

                        /**add code here to update the UI based on item selected**/
                        //i.e. Intent to another page
//                        Snackbar.make(view, "Registering password..", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        //if clicked log out
                        if (menuItem.getItemId() == R.id.nav_log_out){

                            /******** ADD WHAT TO DO WHEN CLICK LOG OUT *****/

                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));


                        }

                        return true;
                    }
                });



        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        int n=1;
        for (int i = 0; i<=3;i++) {

            productList.add(
                    new Product(
                            n=n+i,
                            "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                            "13.3 inch, Silver, 1.35 kg",
                            (float)4.3,
                            60000,
                            R.drawable.macbook));

            productList.add(
                    new Product(
                            n=n+i+1,
                            "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                            "14 inch, Gray, 1.659 kg",
                            (float)4.3,
                            60000,
                            R.drawable.dellinspiron));

            productList.add(
                    new Product(
                            n=n+i+1,
                            "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                            "13.3 inch, Silver, 1.35 kg",
                            (float)4.3,
                            60000,
                            R.drawable.surface));
            //generateData();


        }
        //creating recyclerview adapter
        //ProductAdapter adapter = new ProductAdapter(this, productList);
        adapter = new MyAdapter(recyclerView, this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //CardView cardView = (CardView) findViewById(R.id.tvPoductListing);

    }//END onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    List<Product> items = new ArrayList<>();
//    MyAdapter adapter;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list);
//
//        //random data
//        random10Data();
//
//        //Init View
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyAdapter(recyclerView, this, items);
//        recyclerView.setAdapter(adapter);
//
//        //Set Load more event
//        adapter.setLoadMore(new LoadMore() {
//            @Override
//            public void onLoadMore() {
//                if (items.size()<=20) {
//                    items.add(null);
//                    adapter.notifyItemInserted(items.size() - 1);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            items.remove(items.size() - 1);
//                            adapter.notifyItemRemoved(items.size());
//
//                            //Random more data
//                            int index = items.size();
//                            int end = index + 10;
//                            for (int i = index; i < end; i++) {
//                                generateData();
//                            }
//                            adapter.notifyDataSetChanged();
//                            adapter.setLoaded();
//                        }
//                    }, 5000);
//                }
//                else{
//                        Toast.makeText(ListActivity.this, "Load data completed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//
//    private void random10Data() {
//        //Random data
////        for(int i=0;i<=10;i++) {
////            int id = UUID.randomUUID().hashCode();
////            String name = UUID.randomUUID().toString();
////            String description = "This is a short description of "+name;
////            Random random = new Random();
////            double price = random.nextInt(100-0+1)+0;
////            double rating = random.nextInt(10-0+1)+0;
////            Product item = new Product(
////                    id,
////                    name,
////                    description,
////                    (float)price,
////                    rating
////            );
////            items.add(item);
////        }
//            generateData();
//    }
//
//    public void generateData(){
//            int id = UUID.randomUUID().hashCode();
//            String name = UUID.randomUUID().toString();
//            String description = "This is a short description of "+name;
//            Random random = new Random();
//            double price = random.nextInt(100-0+1)+0;
//            double rating = random.nextInt(10-0+1)+0;
//            int image =0;
//            Product item = new Product(
//                    id,
//                    name,
//                    description,
//                    (float)price,
//                    rating,
//                    image
//            );
//            productList.add(item);
//    }
}//END class



//    private String randomSentence() {
//        String new_sentence = "";
//        for (int i=0;i<26;i++) {
//
//        }
//        return null;


