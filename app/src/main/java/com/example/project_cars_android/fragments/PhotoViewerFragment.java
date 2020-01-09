package com.example.project_cars_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_cars_android.PhotosListAdapter;
import com.example.project_cars_android.R;
import com.example.project_cars_android.models.CarsModel;

import java.util.ArrayList;

public class PhotoViewerFragment extends Fragment {

    RecyclerView photosRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<CarsModel> photosList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photosList = (ArrayList<CarsModel>) getArguments().getSerializable("photoData");
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new PhotosListAdapter(photosList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_viewer, container, false);
        if (photosList != null) {
            photosRecyclerView = view.findViewById(R.id.photosRecyclerView);
            photosRecyclerView.setLayoutManager(layoutManager);
            photosRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

}
