package com.example.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    /**Main */
    private final CustomListeners customListeners;
    private final SwipeState swipeState;

    private final ArrayList<CustomViewModel> list;

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
        return new CustomViewHolder(view, customListeners);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bindDataToViewHolder(list.get(position), position, swipeState);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Retains the swipe state of the cell and updates the UI accordingly. This method ensures that other cells
     * that have been swiped left or right are returned to the middle, while retaining the state of the currently
     * touched cell.
     *
     * @param model The model associated with the swipe action.
     * @param position The position of the item in the data set.
     */
    public void retainSwipe(CustomViewModel model, int position) {
        // Check if swipe is enabled in the current state
        final boolean isEnabled = swipeState == SwipeState.LEFT || swipeState == SwipeState.RIGHT || swipeState == SwipeState.LEFT_RIGHT;
        // If swipe is enabled, reset the swipe state for other cells
        if (isEnabled) {
            for (int index = 0; index < getItemCount(); index++) {
                final boolean isNotSwiped = list.get(index).state != SwipeState.NONE;
                if (index != position && isNotSwiped) {
                    list.get(index).state = SwipeState.NONE;
                    notifyItemChanged(index);
                }
            }
        }
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