package com.example.recyclerviewapp;

public interface CustomListeners {

    public void onClick(CustomViewModel item, int position);

    public void onLongClick(CustomViewModel item, int position);

    public void onItemMoved(CustomViewModel item, int fromPosition, int toPosition);
}
