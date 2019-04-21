package com.example.alexandrebornerand.pretaporter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductPageActivityAlt extends AppCompatActivity {

    private static final String TAG = "ProductPageActivityAlt";
    private String item;
    private TextView title;
    private ImageView img;
    private TextView item_descriptionTV;
    private TextView item_descriptionET;
    private TextView priceTV;
    private EditText priceET;
    private ImageButton editButton;
    private TextView listerName;
    private ArrayList<Product> productArrayList;
    private Product tempProduct;
    private String userID;
    private ImageView userImg;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page_prototype);


        Log.d(TAG, "onCreate: started successfully");

        item = getIntent().getExtras().get("visit_product_page").toString();
//        userID = getIntent().getExtras().get("lister").toString();

        Toast.makeText(getApplicationContext(), "Product: " + item, Toast.LENGTH_SHORT).show();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("listings/" + item);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("users/");
//        storageReference = firebaseStorage.getReference("users/" + firebaseUser.getUid() + "/images/" + item);
        img = (ImageView) findViewById(R.id.product_pic_imgView);
        title = (TextView) findViewById(R.id.item_name_tv);
        item_descriptionET = (TextView) findViewById(R.id.item_description_ET);
        item_descriptionTV = (TextView) findViewById(R.id.item_description_TextView);
        priceET = (EditText) findViewById(R.id.price_EditText);
        editButton = (ImageButton) findViewById(R.id.editBtn);
        listerName = (TextView) findViewById(R.id.lister_name);
        userImg = (ImageView) findViewById(R.id.user_img);
        productArrayList = new ArrayList<>();

        //        Let say you have a reference to the node listings, you can iterate through the nodes as follows:
//        Note that the DataSnapshot child inside the for loop will have the UIDs as key, not listings.
//        firebaseDatabase.getReference("listings").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot child : dataSnapshot.getChildren() ){
//                    // Do magic here
//                    // AttendanceList attendanceList=attendanceSnapshot.getValue(AttendanceList.class);
//                    // attendanceList.name=attendanceSnapshot.child("Name").getValue(String.class);
//                    // attendanceList.mobile=attendanceSnapshot.child("Mobile").getValue(String.class);
//
//                    tempProduct = child.getValue(Product.class);
//                    tempProduct.set_id(child.child("id").getValue(String.class));
//                    tempProduct.set_lister(child.child("lister").getValue(User.class));
//                    productArrayList.add(tempProduct);
//                    //temp.getId();
//
//                }//END for each dataSnapshot
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: database error:"+databaseError);
//            }
//        });
//        userID="";
//        for (int i=0; i<productArrayList.size(); i++) {
//            if (productArrayList.get(i).getId().equals(item)){
//                Product prod = productArrayList.get(i);
//                userID = prod.getLister().getId();
//                title.setText(prod.getName());
//                //TODO: show entire content of description. at the moment size is determining how much is shown
//                item_descriptionET.setText(prod.getDescription());
//
//                Glide.with(getApplicationContext())
//                        .load(storageReference.child(userID+"/images/"+item))
//                        .into(img);
//                String temp = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(prod.getDaily_fee());
//                priceET.setText(temp);
//                listerName.setText(prod.getLister().getFirst_name());
//            }
//        }//END for each product in productArrayList
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value
                Product post = dataSnapshot.getValue(Product.class);
                post.set_id(item);
                //To set lister, need to give it a User object. To get User object, iterate through users and match on userID (was passed in intent)
                post.set_lister(dataSnapshot.child("lister").getValue(User.class));
                //                Log.d(TAG, "Value is: " + post);
                title.setText(post.getName());
                //TODO: show entire content of description. at the moment size is determining how much is shown
                item_descriptionET.setText(post.getDescription());

                String listerID = post.getLister().getId();
                Glide.with(getApplicationContext())
                        .load(storageReference.child(listerID+"/images/"+item))
                        .into(img);
                String temp = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(post.getDaily_fee());
                priceET.setText(temp);
                listerName.setText(post.getLister().getFirst_name());
                Glide.with(getApplicationContext())
                        .load(storageReference.child(listerID+"/profilePicture"))
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImg);
//                priceET.setText(String.format(Locale.getDefault(), "%.2f", post.getDaily_fee()));
//                //show edit button only if post belongs to user
//                if (post.getLister().equals(firebaseUser.getUid())) {
//                    editButton.setVisibility(View.INVISIBLE);
//                }
//                else {
//                    editButton.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }//END onCreate

//    User tempUser;
//    private User getLister() {
//        //TODO: To set lister, need to give it a User object. To get User object, iterate through users and match on userID (was passed in intent)
//        firebaseDatabase.getReference("/users/"+userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User tempUser = dataSnapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return tempUser;
//    }
}//END class ProductPageActivityAlt

