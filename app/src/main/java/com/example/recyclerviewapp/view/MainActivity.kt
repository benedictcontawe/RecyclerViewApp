package com.example.recyclerviewapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.recyclerviewapp.CustomViewModelFactory
import com.example.recyclerviewapp.MainViewModel
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.model.FragmentType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    fun callFragment(fragmentType: Int) {
                Log.e("MainActivity",supportFragmentManager.backStackEntryCount.toString())
                when (fragmentType) {
                    FragmentType.MainFragment -> {
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.container, MainFragment.newInstance())
                                .commitNow()
                    }
                    FragmentType.DetailsFragment -> {
                        supportFragmentManager.beginTransaction()
                                .add(R.id.container, DetailsFragment.newInstance())
                                .addToBackStack("").commit()
                    }
                    else -> {
                        supportFragmentManager.popBackStack()
                    }
                }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0)
            super.onBackPressed()
        else
            supportFragmentManager.popBackStack()
    }
}
