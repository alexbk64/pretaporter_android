package com.example.alexandrebornerand.pretaporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.Rental;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//for darsh multipleImageSelect library
//import com.darsh.multipleimageselect.models.Image;

public class editItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "editItemActivity";
    private static final int REQUEST_CODE = 71;

    private EditText editText_title, editText_desc, editText_price;
    private ImageView imageView_image;
    private Button btn_update_post, btn_dlt_post;
    private ImageButton btn_upload_img;
    private Uri filePath;
    private Uri[] filePaths;
    private final int PICK_IMAGE_REQUEST = 82;
    private EditText minimumDaysET;
    //For darsh multipleImageSelect Library
//    ArrayList<Image> images;
    ArrayList<Uri> images;
    ArrayList<String> downloadUrls;
//    private String downloadUrl;

    DatabaseReference ref;
    String id;
    String title;
    String description;
    String price;
    float finalPrice;
    double rating;
    private Product product;

    private TextView imageSelectorTV;
    private ImageView img1, img2, img3, img4, img5, img6;
    private View view;
    private int img = 0;

    //spinners
    private String categorySelected, colourSelected, sizeSelected;


    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private String uid;
    private User customer;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        //get product from productPage (passed as intent)
        product = (Product) getIntent().getExtras().get("product");

        editText_title = (EditText) findViewById(R.id.title_EditText);
        editText_desc = (EditText) findViewById(R.id.item_description_ET);
        editText_price = (EditText) findViewById(R.id.price_EditText);
        btn_upload_img = (ImageButton) findViewById(R.id.imageButton);
        minimumDaysET = (EditText) findViewById(R.id.minimumDays_EditText);
        btn_update_post = (Button) findViewById(R.id.EditBtn);
        btn_dlt_post = (Button) findViewById(R.id.DeleteBtn);
        imageSelectorTV = (TextView) findViewById(R.id.item_image_TextView);
//        imageView_image = (ImageView) findViewById(R.id.imgView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("/listings/");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("/users/" + uid + "/images/");

        btn_update_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemtoListings();
            }
        });
        btn_dlt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Rental> conflictingRentals = new ArrayList<>();
                //check if product is involved in rental. Cannot delete if product is unavailable now or in the future
                //get all rentals in which user is the lister
                Query query = firebaseDatabase.getReference("rentals").orderByChild("_lister").equalTo(firebaseUser.getUid());
                //check if any of those involve this product
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Rental tempRental = ds.getValue(Rental.class);
                            if (tempRental.get_product().getId().equals(product.getId())){

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date tempStartDate = new Date();
                                Date today = new Date();
                                try {
                                    tempStartDate = sdf.parse(tempRental.get_fromDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //if active, rental is ongoing
                                //if rental is NOT active and start date is after today, rental is upcoming
                                //if either of those conditions are met, delete operation has a conflict
                                if (tempRental.isActive() || (!tempRental.isActive() && tempStartDate.after(today)))
                                    //add to list of conflicting rentals
                                conflictingRentals.add(tempRental);
                            }
                        }
                        //notify user that rental cannot be deleted
                        if (!conflictingRentals.isEmpty())
                            Toast.makeText(editItemActivity.this, "Cannot delete this listing as it is involved in a current or upcoming rental. Please cancel the rental or wait until it is over before trying again.", Toast.LENGTH_LONG).show();

                            //otherwise delete product from database
                        else {
                            databaseReference.child(product.getId()).removeValue().addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: could not delete product " + product.getId() + " from database -" + e);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: deleted product "+product.getId() + " from db");
                                }
                            });
                            //delete stored media associated to product from cloud storage
                            for (int i=0; i<product.getImages().size(); i++) {
                                final int counter = i;
                                storageReference.child(product.getId()).child(String.valueOf(i)).delete().addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: could not delete product " + product.getId() + " media "+counter+" from storage -" + e);
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: deleted " + product.getId() + "'s media "+counter+" from storage");
                                    }
                                });
                            }
                            //update ui
                            startActivity(new Intent(getApplicationContext(), UserListingsActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImages();
            }
        });
        downloadUrls = new ArrayList<>();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 1;
                chooseImage();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 2;
                chooseImage();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 3;
                chooseImage();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 4;
                chooseImage();
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 5;
                chooseImage();
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                img = 6;
                chooseImage();
            }
        });


//        Let say you have a reference to the node users, you can iterate through the nodes as follows:
//        Note that the DataSnapshot child inside the for loop will have the UIDs as key, not users.
        //String id;
        firebaseDatabase.getReference("/users/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //temp.getId();
                customer = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: database error:" + databaseError);
            }

        });

        //TODO: if user clicked on edit item
        //change page title to edit item
        //set text title, description, price, min days
        //load pictures from storage into thumbnails

        editText_title.setText(product.getName());
        editText_desc.setText(product.getDescription());
        editText_price.setText(String.valueOf(product.getDaily_fee()));
        minimumDaysET.setText(String.valueOf(product.getMinimumNumberOfDays()));
        ArrayList<String> prevUris = product.getImages();
        ArrayList<Uri> imagesFromFB = new ArrayList<>();
//        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

        for (String prevUri : prevUris) {
            imagesFromFB.add(Uri.parse(prevUri));
//            bitmapArrayList.add(getImageBitmap(prevUri));

        }
        images = imagesFromFB;
//        setImageThumbnailsFromBitmap(bitmapArrayList);
        setImageThumbnailsFromInternetUri(imagesFromFB);


        //Categories spinner
        categorySelected = product.getCategory();

        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //set previously selected value as current
        int spinnerPosition = adapter.getPosition(categorySelected);
        spinner.setSelection(spinnerPosition);

        //Colours spinner
        colourSelected = product.getColour();
        Spinner colourSpinner = (Spinner) findViewById(R.id.colours_spinner);
        colourSpinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colourAdapter = ArrayAdapter.createFromResource(this,
                R.array.colours_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        colourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        colourSpinner.setAdapter(colourAdapter);
        //set previously selected value as current
        int colourSpinnerPosition = colourAdapter.getPosition(colourSelected);
        colourSpinner.setSelection(colourSpinnerPosition);

        //Sizes spinner
        sizeSelected = product.getSize();
        Spinner sizeSpinner = (Spinner) findViewById(R.id.sizes_spinner);
        sizeSpinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sizeSpinner.setAdapter(sizeAdapter);
        //set previously selected value as current
        int sizeSpinnerPosition = sizeAdapter.getPosition(sizeSelected);
        sizeSpinner.setSelection(sizeSpinnerPosition);

    }//END onCreate()

    private void setImageThumbnailsFromUri(ArrayList<Uri> imageAL) {
        if (imageAL.size() == 1) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);
            img6.setVisibility(View.GONE);
        }
        if (imageAL.size() == 2) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(imageAL.get(1));
            img3.setVisibility(View.GONE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);
            img6.setVisibility(View.GONE);
        }
        if (imageAL.size() == 3) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(imageAL.get(1));
            img3.setVisibility(View.VISIBLE);
            img3.setImageURI(imageAL.get(2));
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);
            img6.setVisibility(View.GONE);
        }
        if (imageAL.size() == 4) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(imageAL.get(1));
            img3.setVisibility(View.VISIBLE);
            img3.setImageURI(imageAL.get(2));
            img4.setVisibility(View.VISIBLE);
            img4.setImageURI(imageAL.get(3));
            img5.setVisibility(View.GONE);
            img6.setVisibility(View.GONE);

        }
        if (imageAL.size() == 5) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(imageAL.get(1));
            img3.setVisibility(View.VISIBLE);
            img3.setImageURI(imageAL.get(2));
            img4.setVisibility(View.VISIBLE);
            img4.setImageURI(imageAL.get(3));
            img5.setVisibility(View.VISIBLE);
            img5.setImageURI(imageAL.get(4));
            img6.setVisibility(View.GONE);

        }
        if (imageAL.size() == 6) {
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(imageAL.get(0));
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(imageAL.get(1));
            img3.setVisibility(View.VISIBLE);
            img3.setImageURI(imageAL.get(2));
            img4.setVisibility(View.VISIBLE);
            img4.setImageURI(imageAL.get(3));
            img5.setVisibility(View.VISIBLE);
            img5.setImageURI(imageAL.get(4));
            img6.setVisibility(View.VISIBLE);
            img6.setImageURI(imageAL.get(5));
        }
    }

    private void setImageThumbnailsFromInternetUri(ArrayList<Uri> imageAL) {
        if (imageAL.size() > 0) {
            img1.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(0)).fitCenter().into(img1);
//            img1.setImageURI(imageAL.get(0));
        }
        if (imageAL.size() > 1) {
            img2.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(1)).fitCenter().into(img2);
//            img2.setImageURI(imageAL.get(1));
        }
        if (imageAL.size() > 2) {
            img3.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(2)).fitCenter().into(img3);
//            img3.setImageURI(imageAL.get(2));
        }
        if (imageAL.size() > 3) {
            img4.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(3)).fitCenter().into(img4);
//            img4.setImageURI(imageAL.get(3));
        }
        if (imageAL.size() > 4) {
            img5.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(4)).fitCenter().into(img5);
//            img5.setImageURI(imageAL.get(4));
        }
        if (imageAL.size() > 5) {
            img6.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imageAL.get(5)).fitCenter().into(img6);
//            img6.setImageURI(imageAL.get(5));
        }
    }


    private void postItemtoListings() {
        ref = databaseReference.child(product.getId());
//        id = ref.getKey();
        title = editText_title.getText().toString();
        description = editText_desc.getText().toString();
        price = editText_price.getText().toString();
        rating = product.getRating();
        downloadUrls = product.getImages();
        String minDays = minimumDaysET.getText().toString();


//        finalPrice = Float.parseFloat(price);
//
//        //upload user selected images
//        uploadImage();
//        //create new product, and push to database
//        Product item = new Product(product.getId(), title, description, finalPrice, rating, downloadUrls, customer, categorySelected, sizeSelected, colourSelected);
//        if (minDays.isEmpty())
//            item.setMinimumNumberOfDays(1);
//        else
//            item.setMinimumNumberOfDays(Integer.parseInt(minDays));
//        ref.setValue(item, new DatabaseReference.CompletionListener() {
//
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                //TODO: add toast or something to notify user on update status
//                if (databaseError == null) {
//                    //success
//                    Toast.makeText(getApplicationContext(), "update successful", Toast.LENGTH_SHORT).show();
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), UserListingsActivity.class));
//                } else {
//                    //push was unsuccessful, notify user
//                    Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        // Reset errors.
        editText_title.setError(null);
        editText_desc.setError(null);
        editText_price.setError(null);
        minimumDaysET.setError(null);
        imageSelectorTV.setError(null);


        boolean cancel = false;
        View focusView = null;

        // Check for a valid item title, if the user entered one.
        if (TextUtils.isEmpty(title)) {
            editText_title.setError(getString(R.string.error_field_required));
            focusView = editText_title;
            cancel = true;
        }
        // Check for a valid description
        if (TextUtils.isEmpty(description)) {
            editText_desc.setError(getString(R.string.error_field_required));
            focusView = editText_desc;
            cancel = true;
        }
        // Check for a valid price
        if (TextUtils.isEmpty(price)) {
            editText_price.setError(getString(R.string.error_field_required));
            focusView = editText_price;
            cancel = true;
        }
        // Check for a valid minimumDays
        if (TextUtils.isEmpty(minDays)) {
            minimumDaysET.setError(getString(R.string.error_field_required));
            focusView = minimumDaysET;
            cancel = true;
        }
        // Check for at least 1 image
        if (images==null) {
            imageSelectorTV.setError(getString(R.string.error_field_required));
            Toast.makeText(this, "You must select at least 1 image", Toast.LENGTH_SHORT).show();
            focusView = btn_upload_img;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            finalPrice = Float.parseFloat(price);
            uploadImage();
            Product item = new Product(product.getId(), title, description, finalPrice, rating, downloadUrls, customer, categorySelected, sizeSelected, colourSelected);
            item.setMinimumNumberOfDays(Integer.parseInt(minDays));
            ref.setValue(item, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    //TODO: add toast or something to notify user on update status
                    if (databaseError == null) {
                        //success
                        Toast.makeText(getApplicationContext(), "update successful", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserListingsActivity.class));
                    } else {
                        //push was unsuccessful, notify user
                        Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }//END postItemToListings

    public void uploadImage() {

        if (images != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            filePaths = new Uri[images.size()];
            for (int i = 0; i < images.size(); i++) {
                final int count = i;
                filePaths[i] = images.get(i);

                if (downloadUrls.contains(images.get(i).toString()))
                    continue;


                storageRef = storageReference.child(product.getId() + "/");
                final StorageReference photoRef = storageRef.child(String.valueOf(i));
                final DatabaseReference dbRef = ref.child("images");


                /*** using storageRef to get download url on condition task was successful***/
                UploadTask uploadTask = photoRef.putFile(filePaths[i]);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        //task was successful, get rid of

                        return photoRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            try {
                                downloadUrls.set(count, downloadUri.toString());
                            } catch (IndexOutOfBoundsException e){
                                downloadUrls.add(downloadUri.toString());
                            }
                            dbRef.setValue(downloadUrls);
                            if (!editItemActivity.this.isFinishing() && progressDialog != null)
                                progressDialog.dismiss();
                            Toast.makeText(editItemActivity.this, "upload successful ", Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle failures
                            Toast.makeText(editItemActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }//END for each image
            if (downloadUrls.size() > images.size()) {
                for (int i=images.size(); i<downloadUrls.size(); i++){
                    downloadUrls.remove(i);
                }
            }
        }//END if images!=null
//        return url;
    }//END uploadImages

    /*** using multipleImageSelect library
     * https://github.com/darsh2/MultipleImageSelect
     */
//    private void chooseImage() {
//        Intent intent = new Intent(this, AlbumSelectActivity.class);
////set limit on number of images that can be selected, default is 10
//        int numberOfImagesToSelect = 6;
//        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, numberOfImagesToSelect);
//        startActivityForResult(intent, Constants.REQUEST_CODE);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            //The array list has the image paths of the selected images
//            images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
//        }
//    }

    /*** using built in image chooser, for single image selection for simplicity **/

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /**** using Matisse Library for multiple image selection
     *https://github.com/zhihu/Matisse
     ****/

    public void chooseImages() {

        Matisse.from(editItemActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .maxSelectable(6)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /************ returning from chooseImages() (multiple) **********/
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            images = (ArrayList<Uri>) Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + images);
            //show user's selected images in thumbnails
            setImageThumbnailsFromUri(images);
        }//END if called by chooseImages
        /************ returning from chooseImage() (single) **********/
        //if called by chooseImage (singular) i.e. user changed one image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            //replace old uri with new uri in images
            images.set(img - 1, filePath);
            //display new image in imageView user clicked on.
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//END onActivityResult

    //Listener for spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected
        switch (parent.getId()){
            case R.id.categories_spinner:
                categorySelected = parent.getItemAtPosition(position).toString();
                break;
            case R.id.sizes_spinner:
                sizeSelected = parent.getItemAtPosition(position).toString();
                break;
            case R.id.colours_spinner:
                colourSelected = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//    private class RetrieveImages extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//
//        public RetrieveImages(ImageView imageView) {
//            this.imageView = imageView;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            return null;
//        }
//    }
}
