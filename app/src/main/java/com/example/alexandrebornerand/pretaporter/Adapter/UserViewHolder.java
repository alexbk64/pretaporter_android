package com.example.alexandrebornerand.pretaporter.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alexandrebornerand.pretaporter.R;

/***not used but implemented for showing lists of users in recyclerview - could be useful ****/


public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name, email, dob, id;

    public UserViewHolder(View userView) {
        super(userView);
        //image = (ImageView)itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.textViewTitle);
        email = itemView.findViewById(R.id.textViewShortDesc);
        dob = itemView.findViewById(R.id.textViewRating);
        id = itemView.findViewById(R.id.textViewPrice);
        image = itemView.findViewById(R.id.imageView);

    }

    public void setImage(Uri uri, ImageView image) {
        Context context = image.getContext();
        //ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(context, ));
        Glide.with(context)
                .load(uri)
                //.placeholder(R.drawable.circle)
                .centerCrop()
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(R.drawable.default_profile_pic)
                .into(image);
    }


}

