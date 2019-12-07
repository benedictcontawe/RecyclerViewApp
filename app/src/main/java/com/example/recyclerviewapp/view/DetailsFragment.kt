package com.example.recyclerviewapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.DetailsBinder
import com.example.recyclerviewapp.model.CustomModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() : DetailsFragment {
          return DetailsFragment()
        }
    }

    private lateinit var binding : DetailsBinder
    private lateinit var item : CustomModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsModel = item
        //binding.title.setText(item.title) //Data Binder Handled this
        //binding.director.setText(item.director) //Data Binder Handled this
        binding.actors.setText(item.actors.forEach{ ", $it"}.toString())
        //binding.genre.setText(item.genre) //Data Binder Handled this
        //binding.ratingText.setText(item.rating.toString()) //Data Binder Handled this
        //binding.release.setText(item.release) //Data Binder Handled this
        //binding.plot.setText(item.plot) //Data Binder Handled this
    }

    fun setDetails(item: CustomModel) {
        this.item = item
    }

    fun closeFragment() {
        getActivity()!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onDestroy() {
        closeFragment()
        super.onDestroy()
    }
}