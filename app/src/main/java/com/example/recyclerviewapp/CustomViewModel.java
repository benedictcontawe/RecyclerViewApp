package com.example.recyclerviewapp;

public class CustomViewModel {

    int icon;
    String name;
    SwipeState state;

    public CustomViewModel(int icon, String name) {
        this.icon = icon;
        this.name = name;
        this.state = SwipeState.NONE;
    }

    public CustomViewModel(int icon, String name, SwipeState state) {
        this.icon = icon;
        this.name = name;
        this.state = state;
    }
}
