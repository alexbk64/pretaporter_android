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

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.util.ArrayList;


public class addItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "addItemActivity";
    private static final int REQUEST_CODE_CHOOSE = 71;

    private EditText editText_title, editText_desc, editText_price;
    private ImageButton btn_upload_img;
    private final int PICK_IMAGE_REQUEST = 82;
    private EditText minimumDaysET;
    ArrayList<Uri> images;
    ArrayList<String> downloadUrls;

    DatabaseReference ref;
    String id;
    String title;
    String description;
    String price;
    float finalPrice;
    double rating;

    private ImageView img1, img2, img3, img4, img5, img6;
    private View view;
    private int img = 0;
    private String categorySelected;
    private String colourSelected;
    private String sizeSelected;
    private TextView imageSelectorTV;


    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private User customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editText_title = (EditText) findViewById(R.id.title_EditText);
        editText_desc = (EditText) findViewById(R.id.item_description_ET);
        editText_price = (EditText) findViewById(R.id.price_EditText);
        btn_upload_img = (ImageButton) findViewById(R.id.imageButton);
        minimumDaysET = (EditText) findViewById(R.id.minimumDays_EditText);
        Button btn_post = (Button) findViewById(R.id.AddBtn);
        imageSelectorTV = (TextView) findViewById(R.id.item_image_TextView);
//        imageView_image = (ImageView) findViewById(R.id.imgView);

        //firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("/listings/");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("/users/" + uid + "/images/");

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemtoListings();
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
                // Do magic here
                //temp.getId();
                customer = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: database error:" + databaseError);
            }

        });


        //Categories spinner
        categorySelected = "";

        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Colours spinner
        colourSelected = "";
        Spinner colourSpinner = (Spinner) findViewById(R.id.colours_spinner);
        colourSpinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colourAdapter = ArrayAdapter.createFromResource(this,
                R.array.colours_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        colourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        colourSpinner.setAdapter(colourAdapter);

        //Sizes spinner
        sizeSelected = "";
        Spinner sizeSpinner = (Spinner) findViewById(R.id.sizes_spinner);
        sizeSpinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sizeSpinner.setAdapter(sizeAdapter);


    }//END onCreate()


    private void postItemtoListings() {
        ref = databaseReference.push();
        id = ref.getKey();
        title = editText_title.getText().toString().trim();
        description = editText_desc.getText().toString().trim();
        price = editText_price.getText().toString().trim();
        //Set default rating to 5, haven't made a way for users to rate others yet
        rating = 5.0;
        String minDays = minimumDaysET.getText().toString().trim();


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
        if (images == null) {
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
            Product item = new Product(id, title, description, finalPrice, rating, downloadUrls, customer, categorySelected, sizeSelected, colourSelected);
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

//            filePaths = new Uri[images.size()];
            for (int i = 0; i < images.size(); i++) {
                StorageReference storageRef = storageReference.child(id + "/");
                final StorageReference photoRef = storageRef.child(String.valueOf(i));
                final DatabaseReference dbRef = ref.child("images");

                /*** using storageRef to get download url on condition task was successful***/
                UploadTask uploadTask = photoRef.putFile(images.get(i));
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
                            downloadUrls.add(downloadUri.toString());
                            dbRef.setValue(downloadUrls);
                            if (!addItemActivity.this.isFinishing() && progressDialog != null)
                                progressDialog.dismiss();
                            Toast.makeText(addItemActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle failures
                            Toast.makeText(addItemActivity.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }//END for each image
        }//END if images!=null
//        return url;
    }//END uploadImages


    /*** using built in image chooser, working **/

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /**** using Matisse Library
     *https://github.com/zhihu/Matisse
     ****/

    public void chooseImages() {

        Matisse.from(addItemActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .maxSelectable(6)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if called by chooseImages() (Matisse)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            images = (ArrayList<Uri>) Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + images);
            //display selected in clickable ImageView views
            if (images.size() > 0) {
                img1.setVisibility(View.VISIBLE);
                img1.setImageURI(images.get(0));
            }
            if (images.size() > 1) {
                img2.setVisibility(View.VISIBLE);
                img2.setImageURI(images.get(1));
            }
            if (images.size() > 2) {
                img3.setVisibility(View.VISIBLE);
                img3.setImageURI(images.get(2));
            }
            if (images.size() > 3) {
                img4.setVisibility(View.VISIBLE);
                img4.setImageURI(images.get(3));
            }
            if (images.size() > 4) {
                img5.setVisibility(View.VISIBLE);
                img5.setImageURI(images.get(4));
            }
            if (images.size() > 5) {
                img6.setVisibility(View.VISIBLE);
                img6.setImageURI(images.get(5));
            }
        }//END if called by chooseImages()
        //if called by chooseImage (single, native) i.e. user changed one image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri filePath = data.getData();
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
        }//END if called by chooseImage()
    }//END onActivityResult

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        switch (parent.getId()) {
            case R.id.categories_spinner:
                categorySelected = parent.getItemAtPosition(position).toString();
            case R.id.sizes_spinner:
                sizeSelected = parent.getItemAtPosition(position).toString();
            case R.id.colours_spinner:
                colourSelected = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
