package com.example.recyclerviewapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> implements CustomItemTouchHelperListener {

    private static String TAG = CustomAdapter.class.getSimpleName();

    /**Main */
    private Context context;
    private CustomListeners customListeners;
    private ItemTouchHelper itemTouchHelper;

    private List<CustomViewModel> list;

    public CustomAdapter(Context context, CustomListeners customListeners) {
        this.context = context;
        this.customListeners = customListeners;
        list = new ArrayList();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_holder, parent, false);
        return new CustomViewHolder(context, view, customListeners, itemTouchHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindDataToViewHolder(list.get(position), position);
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

    public List<CustomViewModel> getItems() {
        return list;
    }

    public void deleteAllItems() {
        list.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }

    public void setTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.d(TAG,"onItemMove($fromPosition, $toPosition)");
        CustomViewModel item = list.get(fromPosition);
        list.remove(item);
        list.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
        customListeners.onItemMoved(list.get(toPosition), fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        deleteItem(position);
    }

    @Override
    public void onItemClear(int position) {
        Log.d(TAG,"onItemDismiss($position)");
    }
}