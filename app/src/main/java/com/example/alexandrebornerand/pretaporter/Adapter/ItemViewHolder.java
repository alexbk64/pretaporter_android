package com.example.alexandrebornerand.pretaporter.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alexandrebornerand.pretaporter.R;

//represents a single listing shown in the recycler view

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout layout;
    public ImageView image;
    public TextView name, short_description, rating, price;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textViewTitle);
        short_description = itemView.findViewById(R.id.textViewShortDesc);
        rating = itemView.findViewById(R.id.textViewRating);
        price = itemView.findViewById(R.id.textViewPrice);
        image = itemView.findViewById(R.id.imageView);
        layout = itemView.findViewById(R.id.rootLayout);


    }

    public void setImage(Uri uri, ImageView image) {
        Context context = image.getContext();
        Glide.with(context.getApplicationContext())
                .load(uri)
                .centerCrop()
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(R.drawable.default_profile_pic)
                .into(image);
    }

    //for recyclerview filtering
    public void show(){
        itemView.setVisibility(View.VISIBLE);
    }
    public void hide() {
        itemView.setVisibility(View.GONE);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
    }

}

