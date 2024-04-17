package com.example.recyclerviewapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recyclerviewapp.databinding.AddBinding;

public class AddFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = AddFragment.class.getSimpleName();
    public static AddFragment newInstance(SupportFragmentManagerListener listener) {
        AddFragment fragment = new AddFragment();
        fragment.listener = listener;
        return fragment;
    }
    private AddBinding binding;
    private MainViewModel viewModel;
    private SupportFragmentManagerListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, getTheme());
        setCancelable(true);
    }

    @Override
    public int getTheme() {
        return R.style.DialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_add, null, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.addButton.getId() && viewModel.isDurationValid(binding.durationEditText.getText().toString())) {
            viewModel.addModel(
                binding.nameEditText.getText().toString(),
                binding.durationEditText.getText().toString()
            );
            dismiss();
        } else {
            Toast.makeText(requireContext(), "Invalid Inputs!", Toast.LENGTH_SHORT).show();
            binding.durationEditText.getText().clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener.removeFragment(TAG);
    }
}