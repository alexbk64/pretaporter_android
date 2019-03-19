package com.example.alexandrebornerand.pretaporter.Adapter;


import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.alexandrebornerand.pretaporter.Interface.LoadMore;
import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.example.alexandrebornerand.pretaporter.R;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

    }

}


class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name, short_description, rating, price;

    public ItemViewHolder(View itemView) {
        super(itemView);
        //image = (ImageView)itemView.findViewById(R.id.imageView);
        name = (TextView)itemView.findViewById(R.id.textViewTitle);
        short_description = (TextView)itemView.findViewById(R.id.textViewShortDesc);
        rating = (TextView)itemView.findViewById(R.id.textViewRating);
        price = (TextView)itemView.findViewById(R.id.textViewPrice);
        image = (ImageView)itemView.findViewById(R.id.imageView);

    }
}
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING=1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Product> items;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<Product> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount=linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading&&totalItemCount<=(lastVisibleItem+visibleThreshold)){
                    if (loadMore!=null)
                        loadMore.onLoadMore();
                }
                isLoading=true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(i == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.card_product_layout,viewGroup,false);
            return new ItemViewHolder(view);
        }
        else if(i == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,viewGroup,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof ItemViewHolder) {
            Product item = items.get(i);
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            //holder.image.setImageResource(ImageView.item.get_image_main());
            holder.name.setText(item.getName());
            holder.short_description.setText(item.getDescription());
            holder.rating.setText(String.valueOf(item.getRating()));
            holder.price.setText(String.valueOf(item.getDaily_fee()));
            holder.image.setImageDrawable(activity.getResources().getDrawable(item.getImage()));

        }
        else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
