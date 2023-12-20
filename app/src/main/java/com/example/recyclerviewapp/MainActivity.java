package com.example.recyclerviewapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomListeners {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setRecyclerView();
        setItems();
    }

    private void setRecyclerView() {
        adapter = new CustomAdapter(this, SwipeState.LEFT_RIGHT);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setItems() {
        itemList = new ArrayList<CustomViewModel>();
        itemList.clear();
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "A"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "B"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "C"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "D"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "E"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "F"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "G"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "H"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "I"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "J"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "K"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "L"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "M"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "N"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "O"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "P"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "Q"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "R"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "S"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "T"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "U"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "V"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "W"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "X"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "Y"));
        itemList.add(new CustomViewModel(R.drawable.ic_person_white, "Z"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setItems(itemList);
    }

    @Override
    public void onClickLeft(CustomViewModel model, int position) {
        Toast.makeText(this,"Left Arrow Clicked! " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickRight(CustomViewModel model, int position) {
        Toast.makeText(this,"Right Arrow Clicked! " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetainSwipe(CustomViewModel model, int position) {
        adapter.retainSwipe(model, position);
    }
}