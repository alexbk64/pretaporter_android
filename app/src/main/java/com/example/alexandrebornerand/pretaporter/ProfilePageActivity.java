package com.example.alexandrebornerand.pretaporter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.Rental;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//TODO: implement profile picture, show registered dob in dob editText

public class ProfilePageActivity extends AppCompatActivity {
    private static final String TAG = "ProfilePageActivity";

    private Calendar myCalendar;
    private EditText dobET;
    private Toolbar mToolbar;
    //drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBar mActionBar;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private String first_name;
    private String surname;
    private Uri photoUrl;
    private TextView mName;
    private TextView mSurname;
    private TextView mNameHdr;
    private ImageView imgHdr;
    private ImageView profilePicImgView;
    private String full_name;
    private String display_name;
    private Date dob;
    private String email;
    private FirebaseUser user;
    private TextView DummyName;
    private User userDB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView numOfListings;
    private TextView numOfRentals;
    private ArrayList<Rental> rentals;
    private ArrayList<Rental> activeRentals;
    private Rental rental;
    private ArrayList<Product> products;
    private RelativeLayout activeRentalsLayout;
    private RelativeLayout itemsForRentLayout;
    private FloatingActionButton fab;
    final private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile_page);
        setContentView(R.layout.drawer_layout_profile_page);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_profile);
        mName = findViewById(R.id.firstName_ET);
        mName.setEnabled(false);
        mSurname = findViewById(R.id.surnameEditText);
        mSurname.setEnabled(false);
        dobET = findViewById(R.id.dobEditText);
        dobET.setEnabled(false);
        display_name = mName.getText().toString();
        DummyName = findViewById(R.id.user_firstName_profilePage);
        numOfListings = (TextView) findViewById(R.id.usr_no_listings);
        numOfRentals = (TextView) findViewById(R.id.usr_no_activeRentals);
        rentals = new ArrayList<>();
        activeRentals = new ArrayList<>();
        products = new ArrayList<>();
        activeRentalsLayout = (RelativeLayout) findViewById(R.id.activeRentalsLayout);
        itemsForRentLayout = (RelativeLayout) findViewById(R.id.itemsForRentLayout);

        //set click listener for Active rentals

        activeRentalsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ProfilePageActivity.this, "going to Rentals", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), RentalsActivity.class));
            }
        });

        //set click listener for items for rent

        itemsForRentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //navigate to user listings page
                startActivity(new Intent(getApplicationContext(), UserListingsActivity.class));
            }
        });


        //userDB = new User(user.)
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/" + user.getUid());
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        if (user.getDisplayName() != null) {
            DummyName.setText(user.getDisplayName());
            String[] name = user.getDisplayName().split(" ");
            if (!name[0].isEmpty())
                mName.setText(name[0]);
            if (name.length > 1) {
                if (!name[1].isEmpty())
                    mSurname.setText(name[1]);
            }
            //TODO: automatically display DOB if not null (shouldn't be if user exists)

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    User userObj = dataSnapshot.getValue(User.class);
//                Log.d(TAG, "Value is: " + post);
                    if (userObj.getDob() != null)
                        dobET.setText(userObj.getDob());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

            //Show number of ****active**** rentals. Onclick will nagivate to rentals page, where user will see both
            //active and inactive rentals
            firebaseDatabase.getReference("rentals/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Rental temp = ds.getValue(Rental.class);
                        if (temp.get_renter().equals(user.getUid())) {
                            rentals.add(temp);
                            if (temp.isActive())
                                activeRentals.add(temp);
                        }
                    }
                    numOfRentals.setText(Integer.toString(activeRentals.size()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

            firebaseDatabase.getReference("listings/").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Product temp = child.getValue(Product.class);
                        if (temp.getLister().getId().equals(user.getUid()))
                            products.add(temp);
                    }//END for each dataSnapshot
                    numOfListings.setText(Integer.toString(products.size()));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: database error:" + databaseError);
                }
            });
        }//END if statement
        profilePicImgView = (ImageView) findViewById(R.id.profilepic_imgView);

        if (user.getPhotoUrl() != null) {
            Glide.with(getApplicationContext())
                    .load(user.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.default_profile_photo_circle)
                    .into(profilePicImgView);
        }
        //profilePicImgView.setImageURI(user.getPhotoUrl());
        else {
            Glide.with(getApplicationContext())
                    .load(storageReference.child("/users/" + user.getUid() + "/profilePicture"))
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.default_profile_photo_circle)
                    .into(profilePicImgView);
        }

        profilePicImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //in edit mode
                if (!mName.isEnabled())
                    return;
                //TODO: upload profile pic
                chooseImage();
            }
        });

//        dobET.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //if not in edit mode
//                if (!dobET.isEnabled())
//                    return;
//                //else, show date picker dialog
//                DialogFragment datePicker = new DatePickerFragment();
//                try {
//                    ((DatePickerFragment) datePicker).setDate(simpleDateFormat.parse(dobET.getText().toString()));
//                }catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                datePicker.show(getSupportFragmentManager(), "date picker");
//            }
//        });
        dobET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                Calendar calendar = Calendar.getInstance();
                try {
                    //set date to user's dob
                    date = simpleDateFormat.parse(dobET.getText().toString());
                } catch (ParseException e) {
                    //set date to today if there is a parse exception
                    date = new Date();
                    e.printStackTrace();
                }
                calendar.setTime(date);
                //get day, month, year from dob and store as separate variables
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfilePageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        startDate.set();
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        Date date = cal.getTime();
                        dobET.setText(simpleDateFormat.format(date));
                    }
                    //set initial date of date picker dialog to currently stored dob
                }, y, m, d);
                DatePicker dp = datePickerDialog.getDatePicker();
                //sets min date to today's date minus 100 years, sets max date to today - 18 years,
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                calendar1.add(Calendar.YEAR, -18);
//                dp.setMaxDate(calendar1.getTimeInMillis());
                calendar1.add(Calendar.YEAR, -82);
//                dp.setMinDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: update user info

                if (user != null) {

                    display_name = mName.getText().toString() + " " + mSurname.getText().toString();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(display_name).build();

                    user.updateProfile(profileUpdates);
                    databaseReference.child("first_name").setValue(mName.getText().toString());
                    databaseReference.child("surname").setValue(mSurname.getText().toString());
                    databaseReference.child("dob").setValue(dobET.getText().toString());
//                    databaseReference.child("profile_picture").setValue();
                    DummyName.setText(display_name);


                    //TODO: section for profile pic
                    uploadImage();

                }
                mName.setEnabled(false);
                mSurname.setEnabled(false);
                dobET.setEnabled(false);
                fab.hide();
                Snackbar.make(view, "Updated to: " + display_name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /** TOOLBAR IMPLEMENTATION **/

        mToolbar = findViewById(R.id.toolbar);
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
        navigationView = findViewById(R.id.nav_view_profile);
        if (navigationView != null) {
            mNameHdr = navigationView.getHeaderView(0).findViewById(R.id.nav_hdr_txtView);
            imgHdr = navigationView.getHeaderView(0).findViewById(R.id.nav_hdr_pPic);
            getUserInfo();
            drawerLayout = findViewById(R.id.drawer_layout_profile);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            //set item as selected to persist highlight
                            menuItem.setChecked(true);
                            //close drawer when item is touched
                            drawerLayout.closeDrawers();


//  Update the UI based on menu item selected

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

//        display_name = user.getDisplayName();
//        email = user.getEmail();
//        dobET = findViewById(R.id.dobEditText);
//        myCalendar = Calendar.getInstance();
//
//        date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//                dob = new Date(dobET.getText().toString());
//            }
//        };
//        dob = new Date(dobET.getText().toString());
//        dobET.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ProfilePageActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)
//                ).show();
//            }
//        });


    }//END onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_page, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_edit:
                //in edit Mode
                if (mName.isEnabled()) {
                    mName.setEnabled(false);
                    mSurname.setEnabled(false);
                    dobET.setEnabled(false);
                    fab.hide();
                    Toast.makeText(this, "Exited edit mode without saving any of your changes...", Toast.LENGTH_SHORT).show();
                } else {
                    mName.setEnabled(true);
                    mSurname.setEnabled(true);
                    dobET.setEnabled(true);
                    fab.show();
                }


        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //profilePicImgView.setImageBitmap(bitmap);
                Glide.with(getApplicationContext())
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePicImgView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //StorageReference ref = storageReference.child("images/"+getRef().get);
            StorageReference ref = storageReference.child("/users/" + user.getUid() + "/profilePicture");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (!ProfilePageActivity.this.isFinishing() && progressDialog != null) {
                                progressDialog.dismiss();
                                Toast.makeText(ProfilePageActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (!ProfilePageActivity.this.isFinishing() && progressDialog != null) {
                                progressDialog.dismiss();
                                Toast.makeText(ProfilePageActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
//                            progressDialog.dismiss();
//                            Toast.makeText(addItemActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

            //update AuthUser photoUri
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "before onSuccess: " + user.getPhotoUrl());
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(uri).build();
                    user.updateProfile(profileUpdates);
                    Log.d(TAG, "after onSuccess: " + uri);
                    //add new photoUrl to db
                    databaseReference.child("photoUrl").setValue(uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure:" + e);
                }
            });


        }
    }

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
            mNameHdr.setText(display_name_hdr);
            Glide.with(getApplicationContext())
//                    .load(storageReference.child("/users/" + user.getUid() + "/profilePicture"))
                    .load(photoUrl_hdr)
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.default_profile_photo_circle)
                    .into(imgHdr);


            /*** STILL NEED TO IMPLEMENT USER PROFILE PIC ***/
            //img.setImageDrawable(LoadImageFromWebOperations(photoUrl.toString()));

//            Picasso.get()
//                        .load(photoUrl.toString())
//                        .placeholder(R.drawable.ic_android)
//                        .resize(100, 100)
//                        .transform(new CircleTransform())
//                        .centerCrop()
//                        .into(img);
        }
    }

    //UPDATE LABEL FOR DOB FIELD
    public void updateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dobET.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    public void genToolbarAndNavView() {


    }

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, dayOfMonth);
//        Date date = calendar.getTime();
//        dobET.setText(simpleDateFormat.format(date));
//    }

    //    private void getUserInfo() {
//        user.
//    }

}
