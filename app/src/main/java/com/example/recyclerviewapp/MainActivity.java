package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener, View.OnKeyListener {

    private ImageView leftArrow,rightArrow;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<CustomViewModel> itemList;
    private int selectedItem;

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
        //recyclerView.setOnKeyListener(this);

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
        selectedItem = 0;
        recyclerView.scrollToPosition(selectedItem);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expandedArrowLeft:
                if (selectedItem > 0) {
                    selectedItem--;
                    Log.e("onClick","Left " + selectedItem);
                    recyclerView.smoothScrollToPosition(selectedItem);
                }
                break;
            case R.id.expandedArrowRight:
                if (selectedItem < adapter.getItemCount()) {
                    selectedItem++;
                    Log.e("onClick","Right " + selectedItem);
                    recyclerView.smoothScrollToPosition(selectedItem);
                }
                break;
            default:
                Log.e("onClick","Default");
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.expandedRecyclerView) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (view.getId() == R.id.expandedRecyclerView) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    Log.e("onKey", "Up");
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    Log.e("onKey", "Left");
                    break;
                case KeyEvent.KEYCODE_DPAD_CENTER & KeyEvent.ACTION_UP:
                    Log.e("onKey", "KEYCODE_DPAD_CENTER, ACTION_UP");
                    break;
                case KeyEvent.KEYCODE_DPAD_CENTER & KeyEvent.ACTION_DOWN:
                    Log.e("onKey", "KEYCODE_DPAD_CENTER , ACTION_DOWN");
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    Log.e("onKey", "Right");
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    Log.e("onKey", "Down");
                    break;
            }
            return true;
        } else {
            Log.e("onKey", "Default");
            return false;
        }
    }
}
