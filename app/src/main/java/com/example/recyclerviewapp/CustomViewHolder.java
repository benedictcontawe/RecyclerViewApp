package com.example.recyclerviewapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewapp.databinding.CellBinder;

class CustomViewHolder extends RecyclerView.ViewHolder {

    private final CellBinder binder;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        binder = CellBinder.bind(itemView);
    }

    public void bindDataToViewHolder(final CustomModel model, final int position) {
        binder.nameTextView.setText(model.name);
        binder.durationTextView.setText(model.duration);
    }
}