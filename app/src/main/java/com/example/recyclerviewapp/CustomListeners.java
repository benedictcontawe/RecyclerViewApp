package com.example.recyclerviewapp;

public interface CustomListeners {

    public void onClickLeft(CustomViewModel model, int position);

    public void onClickRight(CustomViewModel model, int position);

    public void onRetainSwipe(CustomViewModel model, int position);
}