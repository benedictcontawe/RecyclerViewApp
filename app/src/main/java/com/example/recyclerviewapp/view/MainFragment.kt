package com.example.recyclerviewapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.*
import com.example.recyclerviewapp.databinding.MainBinder
import com.example.recyclerviewapp.model.CustomViewModel
import com.example.recyclerviewapp.model.FragmentType


class MainFragment : Fragment(), CustomListeners {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainBinder
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,container,false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, CustomViewModelFactory(getContext()!!, this)).get(MainViewModel::class.java)
        binding.setViewModel(viewModel)

        viewModel.setItems()
        setRecylerView()
    }

    private fun setRecylerView() {
        binding.recyclerView.setLayoutManager(LinearLayoutManager(getContext()!!, RecyclerView.VERTICAL, false))
        viewModel.getAdapter().observe(this,object : Observer<CustomAdapter> {
            override fun onChanged(adapter : CustomAdapter) {
                binding.recyclerView.setAdapter(adapter)
            }
        })
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onClick(item: CustomViewModel, position: Int) {
        (activity as MainActivity).callFragment(FragmentType.DetailsFragment)
    }
}
