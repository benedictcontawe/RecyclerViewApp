package com.example.recyclerviewapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.model.CustomModel

class MainActivity : AppCompatActivity() {

    private lateinit var detailsFragment: DetailsFragment
    private lateinit var reviewFragment: ReviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            callMainFragment()
        }
    }

    private fun callMainFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
    }

    fun callDetailsFragment(item: CustomModel) {
        Log.e("MainActivity",supportFragmentManager.backStackEntryCount.toString())
        detailsFragment = DetailsFragment.newInstance()
        detailsFragment.setDetails(item)
        supportFragmentManager.beginTransaction()
                .add(R.id.container, detailsFragment)
                .addToBackStack("").commit()
    }

    fun callReviewFragment(item: CustomModel) {
        reviewFragment = ReviewFragment.newInstance()
        reviewFragment.setDetails(item = item)
        supportFragmentManager.beginTransaction()
                .add(R.id.container, reviewFragment)
                .addToBackStack("").commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
