package com.example.project_cars_android;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ablanco.zoomy.Zoomy;
import com.example.project_cars_android.models.CarsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.PhotosListViewHolder> {

    private ArrayList<CarsModel> mDataset;

    static class PhotosListViewHolder extends  RecyclerView.ViewHolder {

        ImageView photoItemImageView;

        PhotosListViewHolder(final View itemView) {
            super(itemView);
            photoItemImageView = itemView.findViewById(R.id.photoItemImageView);
        }
    }
    public PhotosListAdapter(ArrayList<CarsModel> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public PhotosListAdapter.PhotosListViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        PhotosListViewHolder viewHolder = new PhotosListViewHolder(view);
        Zoomy.Builder builder = new Zoomy.Builder((Activity) view.getContext()).target(view).enableImmersiveMode(false);
        builder.register();
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final PhotosListAdapter.PhotosListViewHolder photosListViewHolder, final int i) {
        final CarsModel photoId = mDataset.get(i);
        Picasso.get().load(photoId.getPhotoUrl()).into(photosListViewHolder.photoItemImageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
