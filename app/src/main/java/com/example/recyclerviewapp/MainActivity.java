package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    private ImageView leftArrow,rightArrow;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<CustomViewModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.expandedRecyclerView);
        leftArrow = (ImageView) findViewById(R.id.expandedArrowLeft);
        rightArrow = (ImageView) findViewById(R.id.expandedArrowRight);

        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
        recyclerView.setOnTouchListener(this);

        setRecylerView();
        setItems();
    }

    private void setRecylerView() {
        adapter = new CustomAdapter(this);
        //recyclerView.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayout.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setItems() {
        itemList = new ArrayList<CustomViewModel>();
        itemList.add(new CustomViewModel("A"));
        itemList.add(new CustomViewModel("B"));
        itemList.add(new CustomViewModel("C"));
        itemList.add(new CustomViewModel("D"));
        itemList.add(new CustomViewModel("E"));
        itemList.add(new CustomViewModel("F"));
        itemList.add(new CustomViewModel("G"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setItems(itemList);
    }

    @Override
    public void onClick(View view) {
        //TODO: use smooth scroll for super smooth scroll
        switch (view.getId()) {
            case R.id.expandedArrowLeft:
                Log.e("onClick","Left");
                break;
            case R.id.expandedArrowRight:
                Log.e("onClick","Right");
                break;
            default:
                Log.e("onClick","Default");
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
