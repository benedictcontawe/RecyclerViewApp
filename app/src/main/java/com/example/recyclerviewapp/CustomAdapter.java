package com.example.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    public static String TAG = CustomAdapter.class.getSimpleName();

    private final List<CustomModel> models;

    public CustomAdapter() {
        this.models = new ArrayList<CustomModel>();
        setHasStableIds(true);
    }

    public CustomAdapter(List<CustomModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindDataToViewHolder(models.get(position), position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public long getItemId(int position) {
        return models.get(position).id;
    }

    public void setModels(List<CustomModel> models) {
        this.models.clear();
        this.models.addAll(models);
        notifyDataSetChanged();
    }
}
