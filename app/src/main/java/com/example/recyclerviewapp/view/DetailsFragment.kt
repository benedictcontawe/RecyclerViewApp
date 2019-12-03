package com.example.recyclerviewapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.DetailsBinder

class DetailsFragment : Fragment() {
    //TODO: Finish binding data to the respective UI components
    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var binding : DetailsBinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment,container,false)
        return binding.root
    }

}