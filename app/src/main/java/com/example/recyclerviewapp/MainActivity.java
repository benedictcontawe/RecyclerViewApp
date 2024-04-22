package com.example.recyclerviewapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.example.recyclerviewapp.databinding.MainBinder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SupportFragmentManagerListener {

    private MainBinder binder;
    private MainViewModel viewModel;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new CustomAdapter();
        binder.recyclerView.setAdapter(adapter);
        final int dividerHeight = 1; // Adjust divider height as needed
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        binder.recyclerView.addItemDecoration(new DividerItemDecoration(dividerHeight, dividerDrawable));
        binder.floatingActionButtonAdd.setOnClickListener(this::onClick);
        binder.floatingActionButtonPlayPause.setOnClickListener(this::onClick);
        viewModel.observeModels().observe(MainActivity.this, new Observer<List<CustomModel>>() {
            @Override
            public void onChanged(List<CustomModel> models) {
                adapter.setModels(models);
            }
        });
        viewModel.observeAnimatedVectorDrawable().observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isPlay) {
                binder.floatingActionButtonPlayPause.setImageDrawable(viewModel.getAnimatedVectorDrawableCompat(isPlay));
                final Animatable2Compat animatable = (Animatable2Compat) binder.floatingActionButtonPlayPause.getDrawable();
                animatable.start();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binder.floatingActionButtonAdd.getId()) {
            showDialogFragment(AddFragment.newInstance(this), AddFragment.TAG);
        } else if (view.getId() == binder.floatingActionButtonPlayPause.getId()) {
            viewModel.toggleAnimatedVectorDrawable();
        }
    }

    @Override
    public void showDialogFragment(DialogFragment fragment, String tag) {
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void removeFragment(String tag) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}