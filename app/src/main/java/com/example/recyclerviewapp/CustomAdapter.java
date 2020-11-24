package com.example.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static String TAG = CustomAdapter.class.getSimpleName();
    /**Main */
    private CustomListeners customListeners;
    private SwipeState swipeState;

    private ArrayList<CustomViewModel> list;

    public CustomAdapter(CustomListeners customListeners, SwipeState swipeState) {
        super();
        this.customListeners = customListeners;
        this.swipeState = swipeState;
        list = new ArrayList<CustomViewModel>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cell, parent, false);
        return new CustomViewHolder(parent.getContext(), view, customListeners);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bindDataToViewHolder(list.get(position), position, swipeState);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<CustomViewModel> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void insertItem(CustomViewModel item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void insertItems(List<CustomViewModel> items, int position) {
        list.addAll(items);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void updateItem(CustomViewModel item, int position) {
        list.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAllItems() {
        list.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }
}