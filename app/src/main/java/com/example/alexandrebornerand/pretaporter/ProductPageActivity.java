package com.example.alexandrebornerand.pretaporter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.Rental;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * REF: https://developer.android.com/training/basics/intents/result
 */
public class ProductPageActivity extends AppCompatActivity {

    private static final String TAG = "ProductPageActivity";
    private String item;
    private String filter;
    private TextView title;
    private ImageView img;
    private TextView item_descriptionTV;
    private TextView item_descriptionET;
    private TextView priceTV;
    private EditText priceET;
    private Button editButton;
    private TextView listerName;
    private ArrayList<Product> productArrayList;
    private Product tempProduct;
    private ImageView userImg;
    private CarouselView carouselView;
    private String listerID;
    //    private int[] images;
    private ArrayList<String> mImages;
    private Button availabilityBtn;
    private boolean prodIsAvailable;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<Date> dateArrayList;
    private Product product;
    private SimpleDateFormat simpleDateFormat;
    private TextView colour;
    private TextView category;
    private TextView size;
    private TextView exchangeMethod;
    private TextView minDays;





    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


//    int[] images = {R.drawable.default_profile_pic, R.drawable.macbook, R.drawable.iphone8, R.drawable.surface, R.drawable.navy_suit, R.drawable.navy_suit_try};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Log.d(TAG, "onCreate: started successfully");

        //Toolbar imp
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);

        //get selected item from intent
        item = getIntent().getExtras().get("visit_product_page").toString();
        //get selected filter from intent
        filter = getIntent().getExtras().get("filter").toString();

//        userID = getIntent().getExtras().get("lister").toString();

        Toast.makeText(getApplicationContext(), "Product: " + item, Toast.LENGTH_SHORT).show();



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("listings/" + item);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("users/");
//        storageReference = firebaseStorage.getReference("users/" + firebaseUser.getUid() + "/images/" + item);
//        img = (ImageView) findViewById(R.id.product_pic_imgView);
        title = (TextView) findViewById(R.id.title_TextView);
//        item_descriptionET = (TextView) findViewById(R.id.item_description_ET);
        item_descriptionTV = (TextView) findViewById(R.id.item_description_TextView);
        priceTV = (TextView) findViewById(R.id.price_TV);
        listerName = (TextView) findViewById(R.id.lister_name);
        userImg = (ImageView) findViewById(R.id.user_img);
        colour = (TextView) findViewById(R.id.item_colour);
        category = (TextView) findViewById(R.id.item_category);
        size = (TextView) findViewById(R.id.item_size);
        exchangeMethod = (TextView) findViewById(R.id.exchangeMethod_TV);
        minDays = (TextView) findViewById(R.id.item_minDays);
        productArrayList = new ArrayList<>();
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        //TODO: once ready to remove trials from DB, update all simple date formats to dd-MM-yyyy so they are the same across the app
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");





        //Buttons
        availabilityBtn = (Button) findViewById(R.id.availability_btn);
        editButton = (Button) findViewById(R.id.EditBtn);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value
                Product post = dataSnapshot.getValue(Product.class);
                product = post;
                if (post==null) return;
                mImages = post.getImages();
                Log.d(TAG, "onDataChange: images:" + mImages);
                title.setText(post.getName());
                item_descriptionTV.setText(post.getDescription());
                listerID = post.getLister().getId();
                //convert daily fee into local currency format
                String temp = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(post.getDaily_fee());
                //To display %.2f format, without currency logo from locale
//                priceET.setText(String.format(Locale.getDefault(), "%.2f", post.getDaily_fee()));
//
                priceTV.setText(temp);
                listerName.setText(post.getLister().getFirst_name());
//                if (post.getMinimumNumberOfDays()==null)

                //had to include this to account for facebook and google users not having a profile picture in storage
                if (post.getLister().getPhotoUrl()!=null)
                    Glide.with(getApplicationContext())
                            .load(Uri.parse(post.getLister().getPhotoUrl()))
                            .apply(RequestOptions.circleCropTransform())
                            .error(R.drawable.default_profile_photo_circle)
                            .into(userImg);
                else
                Glide.with(getApplicationContext())
                        .load(storageReference.child(listerID + "/profilePicture"))
//                        .load(Uri.parse(post.getLister().getPhotoUrl()))
                        .apply(RequestOptions.circleCropTransform())
                        .error(R.drawable.default_profile_photo_circle)
                        .into(userImg);


                colour.setText(product.getColour());
                category.setText(product.getCategory());
                size.setText(product.getSize());
                //Use String.valueOf because product.getMinimumNumberOfDays() returns an int
                minDays.setText(String.valueOf(product.getMinimumNumberOfDays()));
//                minDays.setText(product.getMinimumNumberOfDays());

                //havent given user option to set exchange methods so hardcoding for now. TODO: remove later
                exchangeMethod.setText("Pick up/drop off: dry cleaner");

                //TODO: show edit button only if post belongs to user, implement edit method
//                //show edit button only if post belongs to user
//                if (post.getLister().equals(firebaseUser.getUid())) {
//                    editButton.setVisibility(View.INVISIBLE);
//                }
//                else {
//                    editButton.setVisibility(View.VISIBLE);
//                }

                //if listerID == uid, post belongs to user so show edit button
                //Buttons again
                if (firebaseUser.getUid().equals(listerID)) {
                    //post belongs to current user. show edit button instead of availability.
                    availabilityBtn.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);

                }
                //if doesn't belong to user, show check availability button
                else {
                    availabilityBtn.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);
                }

                carouselView.setImageListener(imageListener);
                carouselView.setPageCount(mImages.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        availabilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calendar = Calendar.getInstance();
////                int d = calendar.get(Calendar.DAY_OF_MONTH);
////                int m = calendar.get(Calendar.MONTH);
////                int y = calendar.get(Calendar.YEAR);
////                datePickerDialog = new DatePickerDialog(ProductPageActivity.this, new DatePickerDialog.OnDateSetListener() {
////                    @Override
////                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//////                        startDate.set();
////                    }
////                }, y,m,d);
////                DatePicker dp = datePickerDialog.getDatePicker();
////                //sets today's date as minimum date
////                dp.setMinDate(System.currentTimeMillis()-1000);
////                datePickerDialog.show();
                Intent goToDateSelector = new Intent(getApplicationContext(), dateSelectorActivity.class);
                goToDateSelector.putExtra("product", product);
                startActivityForResult(goToDateSelector, 1);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductPageActivity.this, "You clicked on Edit item!", Toast.LENGTH_SHORT).show();
                Intent goToEditPage = new Intent(getApplicationContext(), editItemActivity.class);
                goToEditPage.putExtra("product", product);
//                startActivityForResult(goToEditPage, 5);
                finish();
                startActivity(goToEditPage);
            }
        });

    }//END onCreate


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            //TODO: Delete line below to centerCrop by default.
            //setScaleType() overrides the glide scale options so can't use both
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (mImages != null) {
//            imageView.setImageResource(images[position]);
//            imageView.setImageURI(Uri.parse(mImages.get(position)));
                Glide.with(getApplicationContext())
                        .load(mImages.get(position))
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(R.drawable.default_profile_pic)
                        .into(imageView);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //TODO: return previously set filter
            Intent returnIntent = new Intent();
            returnIntent.putExtra("filter", filter);
            setResult(Activity.RESULT_OK,returnIntent);
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (item.getItemId() == R.id.action_favourite) {
            //TODO: Add to favourites, fill icon, retain state
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /********** Returning from dateSelectorActivity**********/
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final ArrayList<String> selectedDates = data.getStringArrayListExtra("result");
//                Toast.makeText(ProductPageActivity.this, selectedDates.toString(),
//                        Toast.LENGTH_LONG).show();
//                String result=data.getStringExtra("result");
                if (selectedDates != null) {

                    //TODO: if listerID = userId, item belongs to user, do something else
                    //maybe provide an edit button instead of check availability

                    final String startDate = selectedDates.get(0);
                    final String endDate = selectedDates.get(selectedDates.size() - 1);
                    Toast.makeText(ProductPageActivity.this, "date range: " + startDate + "-" + endDate,
                            Toast.LENGTH_LONG).show();

                    //OK SO DATES ARE WORKING, now have selected date range
                    //TODO: change text on button to something like "rent item for selected dates"
                    availabilityBtn.setText("Rent this item from "+startDate+" until "+endDate);
                    //TODO: use the start and end date of the date range to create a rental object when button is clicked
                    //new onclick listener for updated button
                    availabilityBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: create a rental object, give it start and end dates and set item availability as false between these dates
//                            Toast.makeText(ProductPageActivity.this, "rental started",
//                                    Toast.LENGTH_LONG).show();


                            //If not yet unavailable for any dates
//                            if (product.getDatesUnavailable() == null){
//                                product.setDatesUnavailable(selectedDates);
//
//                            }
//                            //if already unavailable for certain dates
//                            else if (product.getDatesUnavailable().size()>0){
//                                product.updateDatesUnavailable(selectedDates);
//                            }

                            //maybe better to call only updateDatesUnavailable(), and deal with lists in Product class
                            product.updateDatesUnavailable(selectedDates);
                            try {
                                databaseReference.setValue(product);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            float totalAmount = product.getDaily_fee() * selectedDates.size();
                            DatabaseReference rentalsRef = firebaseDatabase.getReference("rentals").push();
                            Rental rental = new Rental(listerID, firebaseUser.getUid(), totalAmount, rentalsRef.getKey(), startDate, endDate, product);
                            rental.setDaysRented(getDateArrayList(selectedDates));
//                            rental.initialiseActiveStatus();
                            rentalsRef.setValue(rental, new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    //TODO: add toast or something to notify user on update status
                                    if (databaseError == null) {
                                        //success
                                        Toast.makeText(getApplicationContext(), "update successful, rental object created", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), RentalsActivity.class));
                                    } else {
                                        //push was unsuccessful, notify user
                                        Toast.makeText(getApplicationContext(), "update failed, object not created", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onComplete: databaseError: " + databaseError);
                                    }
                                }
                            });
                        }
                    });
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.d(TAG, "onActivityResult: no date selected");
            }
        }

        //seems like no need to return for result
//        /********** Returning from editItemActivity**********/
//        else if (resultCode==5){
//
//        }
    }//onActivityResult


    //helper method to convert an Arraylist<String> object into ArrayList<Date>
    private ArrayList<Date> getDateArrayList(ArrayList<String> stringArrayList){
        ArrayList<Date> dateArrayList = new ArrayList<>();
        //iterate through stringArrayList, parse each element into a temporary Date instance, and add this instance to dateArrayList
        for (String stringDate : stringArrayList) {
            try {
                Date temp = simpleDateFormat.parse(stringDate);
                dateArrayList.add(temp);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return dateArrayList;
    }//END getDateArrayList()

}//END class ProductPageActivityAlt

