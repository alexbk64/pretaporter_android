package com.example.alexandrebornerand.pretaporter.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.alexandrebornerand.pretaporter.Model.Rental;
import com.example.alexandrebornerand.pretaporter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RentalViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private ArrayList<Rental> mRentals;

    public RecyclerViewAdapter(Context mContext, ArrayList<Rental> mRentals) {
        this.mContext = mContext;
        this.mRentals = mRentals;
    }

    @NonNull
    @Override
    public RentalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.card_rental_layout, viewGroup, false);
        return new RentalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RentalViewHolder rentalViewHolder, int i) {
        rentalViewHolder.listerFirstName.setText(mRentals.get(i).get_product().getLister().getFirst_name());
        rentalViewHolder.listingTitle.setText(mRentals.get(i).get_product().getName());
        rentalViewHolder.startDate.setText(mRentals.get(i).get_fromDate());
        rentalViewHolder.endDate.setText(mRentals.get(i).get_toDate());
        //display price in local currency format
        String temp = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(mRentals.get(i).get_amount());
        rentalViewHolder.totalRentalCost.setText(temp);


        //Using glide library to load images
        //lister profile pic
        if (mRentals.get(i).get_product().getLister().getPhotoUrl() != null)
            Glide.with(rentalViewHolder.listerProfilePhoto.getContext())
                    .load(Uri.parse(mRentals.get(i).get_product().getLister().getPhotoUrl()))
//                    .circleCrop()
                    .fitCenter()
                    .error(R.drawable.default_profile_photo_circle)
                    .apply(RequestOptions.circleCropTransform())
                    .into(rentalViewHolder.listerProfilePhoto);

        //Listing image (from FB storage)
        StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("users/" + mRentals.get(i).get_lister() + "/images/" + mRentals.get(i).get_product().getId() + "/" + 0);
        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //reference to file made successfully, use uri to load photo
                rentalViewHolder.setImage(uri, rentalViewHolder.listingImage);
                Log.d(TAG, "onBindViewHolder: photoUri = " + uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //do nothing
                //photoUrl=null;
                Log.w(TAG, "onFailure: failed to get downloadUri", e);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRentals.size();
    }

    public static class RentalViewHolder extends RecyclerView.ViewHolder {
        private ImageView listingImage;
        private TextView listerFirstName;
        private TextView listingTitle;
        private TextView startDate;
        private TextView endDate;
        private TextView totalRentalCost;
        private ImageView listerProfilePhoto;

        public RentalViewHolder(@NonNull View itemView) {
            super(itemView);
            listingTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            listerFirstName = (TextView) itemView.findViewById(R.id.lister_name);
            startDate = (TextView) itemView.findViewById(R.id.rental_date_start);
            endDate = (TextView) itemView.findViewById(R.id.rental_date_end);
            listingImage = (ImageView) itemView.findViewById(R.id.listingImageView);
            listerProfilePhoto = (ImageView) itemView.findViewById(R.id.user_img);
            totalRentalCost = (TextView) itemView.findViewById(R.id.rental_cost);
        }

        public void setImage(Uri uri, ImageView image) {
            Context context = image.getContext();
            Glide.with(context.getApplicationContext())
                    .load(uri)
                    .centerCrop()
//                .fitCenter()
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .error(R.drawable.default_profile_pic)
                    .into(image);
        }
    }
    //without this, recyclerview doesn't automatically load data
    public void setList(ArrayList<Rental> mList){
        this.mRentals = mList;
        notifyDataSetChanged();
    }
}//END RecyclerViewAdapter class
