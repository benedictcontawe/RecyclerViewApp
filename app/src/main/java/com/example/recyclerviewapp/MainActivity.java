package com.example.recyclerviewapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomListeners {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private CustomItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;
    private List<CustomViewModel> itemList;

    private static String TAG = MainActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setRecylerView();
        setItems();
        adapter.setItems(itemList);
    }

    private void setRecylerView() {
        adapter = new CustomAdapter(this, this);

        itemTouchHelperCallback = new CustomItemTouchHelperCallback(adapter, true, true);
        itemTouchHelperCallback.setFadeOutSwipe(true);
        itemTouchHelperCallback.setTransparentDrag(true);

        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);

        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setItems() {
        itemList = new ArrayList();;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter.setItems(itemList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnScrollListener(setScrollListener());
        /*adapter.getItems().map {
            Log.e(TAG,"${it.name}")
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnScrollListener(setScrollListener());
    }

    @Override
    public void onClick(CustomViewModel item, int position) {
        Toast.makeText(this,"onClick " + item.name + " " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(CustomViewModel item, int position) {
        Toast.makeText(this,"onLongClick " + item.name + " " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemMoved(CustomViewModel item, int fromPosition, int toPosition) {
        //Toast.makeText(this,"onItemMoved ${item.name} $fromPosition $toPosition",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onItemMoved ${item.name} $fromPosition $toPosition");
    }

    private RecyclerView.OnScrollListener setScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean canScrollUp = recyclerView.canScrollVertically(-1);
                boolean canScrollDown = recyclerView.canScrollVertically(1);
                if (canScrollUp && canScrollDown) {
                    Log.d(TAG,"Recycler View at Middle");
                } else if (canScrollDown && !canScrollUp) {
                    Log.d(TAG,"Recycler View top reached");
                } else if (canScrollUp && !canScrollDown) {
                    Log.d(TAG,"Recycler View bottom reached");
                }

                if (dy > 0) {
                    Log.d(TAG,"Recycler View Scrolling Down");
                } else if (dy < 0) {
                    Log.d(TAG,"Recycler View Scrolling Up");
                }
            }
        };
    }
}