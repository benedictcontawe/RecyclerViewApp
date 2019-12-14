package com.example.recyclerviewapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.ReviewBinder
import com.example.recyclerviewapp.model.CustomModel

class ReviewFragment : Fragment(), View.OnClickListener{

    companion object {
        fun newInstance() : ReviewFragment {
            return ReviewFragment()
        }
    }

    private lateinit var binding : ReviewBinder
    private lateinit var item : CustomModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.review_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setReviewModel(item)//binding.reviewModel = item
        //binding.title.setText(item.title) //Data Binder Handled this
        binding.ratingBar.setRating(5f)
        binding.review.setText("")
        binding.rateMovie.setOnClickListener(this)
    }

    fun setDetails(item: CustomModel) {
        this.item = item
    }

    override fun onClick(view: View) {
        Toast.makeText(context,"Movie Rated!",Toast.LENGTH_SHORT).show()
    }

    fun closeFragment() {
        getActivity()!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onDestroy() {
        closeFragment()
        super.onDestroy()
    }
}