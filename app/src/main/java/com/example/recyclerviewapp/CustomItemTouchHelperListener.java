package com.example.recyclerviewapp;

public interface CustomItemTouchHelperListener {

    public void onItemMove(int fromPosition, int toPosition);

    public void onItemSwiped(int position);

    public void onItemClear(int position);
}
