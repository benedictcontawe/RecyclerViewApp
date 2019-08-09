package com.example.recyclerviewapp;

interface CustomListeners {

    void onClick(CustomViewModel item, int position);

    void  onLongClick(CustomViewModel item, int position);
}
