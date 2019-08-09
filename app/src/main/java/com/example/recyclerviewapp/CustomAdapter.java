package com.example.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private CustomListeners customListeners;
    private List<CustomViewModel> list;

    public CustomAdapter(Context context, CustomListeners customListeners) {
        super();
        this.context = context;
        this.customListeners = customListeners;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_sample, parent, false);

        return new CustomViewHolder(context,view, customListeners);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindDataToViewHolder(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<CustomViewModel> items) {
        list = items;
        //list.clear();
        //list.addAll(items);
        notifyDataSetChanged();
    }

    public void insertItem(CustomViewModel item, int position) {
        list.add(position,item);
        notifyItemInserted(position);
    }

    public void insertItems(List<CustomViewModel> items, int position) {
        list.addAll(items);
        notifyItemRangeChanged(position,getItemCount());
    }

    public void updateItem(CustomViewModel item, int position) {
        list.set(position,item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAllItems() {
        list.clear();
        notifyItemRangeRemoved(0,getItemCount());
    }
}
