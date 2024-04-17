package com.example.recyclerviewapp;

public class CustomModel {
    long id;
    String name, duration;

    public CustomModel(String name, String duration) {
        this.id = (long) (Math.random() * Long.MAX_VALUE);
        this.name = name;
        this.duration = duration;
    }

    public CustomModel(long id, String name, String duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }
}