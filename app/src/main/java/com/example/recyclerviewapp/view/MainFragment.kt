package com.example.recyclerviewapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.*
import com.example.recyclerviewapp.databinding.MainBinder
import com.example.recyclerviewapp.model.CustomModel
import com.example.recyclerviewapp.view.adapter.CustomAdapter


class MainFragment : Fragment(), CustomListeners {
    //This Fragment Use Data Binding with ViewModel and use live data to observe adapter of recycler view
    companion object {
        fun newInstance() : MainFragment = MainFragment()
    }

    private lateinit var binding: MainBinder
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(getActivity()!!, CustomViewModelFactory(context!!, this)).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.setItems()
        setRecylerView()
    }

    private fun setRecylerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        viewModel.getAdapter().observe(getViewLifecycleOwner(),object : Observer<CustomAdapter> {
            override fun onChanged(adapter : CustomAdapter) {
                binding.recyclerView.adapter = adapter
            }
        })
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onClick(item: CustomModel, position: Int) {
        (activity as MainActivity).callDetailsFragment(item)
    }
}
