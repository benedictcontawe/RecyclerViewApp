package com.example.recyclerviewapp;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public interface SupportFragmentManagerListener {
    //public void addFragment(int containerViewId, Fragment fragment, String tag);
    //public void replaceFragment(int containerViewId, Fragment fragment);
    //public void addToBackStackFragment(int containerViewId, Fragment fragment);
    public void removeFragment(String tag);
    //public void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment, String tag);
    public void showDialogFragment(DialogFragment fragment, String tag);
    //public void onPopBackStackFragment();
}