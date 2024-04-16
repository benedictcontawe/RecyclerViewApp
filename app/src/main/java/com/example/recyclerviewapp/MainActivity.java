package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomListeners {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<CustomViewModel> itemList;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setRecylerView();
        setItems();
    }

    private void setRecylerView() {
        adapter = new CustomAdapter(this,this);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayout.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setItems() {
        itemList = new ArrayList<CustomViewModel>();
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "A"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "B"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "C"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "D"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "E"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "F"));
        itemList.add(new CustomViewModel(R.drawable.ic_launcher_foreground, "G"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setItems(itemList);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(CustomViewModel item, int position) {
        Toast.makeText(this,"onClick " + item.names + " " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(CustomViewModel item, int position) {
        Toast.makeText(this,"onLongClick " + item.names + " " + position,Toast.LENGTH_SHORT).show();
    }
}