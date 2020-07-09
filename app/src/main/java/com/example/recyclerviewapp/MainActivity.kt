package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomListeners {

    private lateinit var nestedAdapter : CustomNestedAdapter
    private lateinit var itemHorizontalList : MutableList<CustomViewModel>
    private lateinit var itemVerticalList : MutableList<CustomViewModel>

    companion object {
        private var TAG : String = MainActivity::class.java.simpleName

        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecylerView()
        setItems()
    }

    private fun setRecylerView() {
        nestedAdapter = CustomNestedAdapter(this)
        recycler_view_nested.setAdapter(nestedAdapter)
        recycler_view_nested.setHasFixedSize(true)
    }

    private fun setItems() {
        itemHorizontalList = mutableListOf<CustomViewModel>()
        itemHorizontalList.clear()
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "1"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "2"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "3"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "4"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "5"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "6"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "7"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "8"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "9"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "10"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "11"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "12"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "13"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "14"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "15"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "16"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "17"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "18"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "19"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "20"))

        itemVerticalList = mutableListOf<CustomViewModel>()
        itemVerticalList.clear()
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "A"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "B"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "C"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "D"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "E"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "F"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "G"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "H"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "I"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "J"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "K"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "L"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "M"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "N"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "O"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "P"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "Q"))
    }

    override fun onStart() {
        super.onStart()
        nestedAdapter.setVerticalItems(itemVerticalList)
        nestedAdapter.setHorizontalItems(itemHorizontalList)
    }

    override fun onResume() {
        super.onResume()
        recycler_view_nested.addOnScrollListener(setScrollListener())
    }

    override fun onPause() {
        super.onPause()
        recycler_view_nested.removeOnScrollListener(setScrollListener())
    }

    override fun onClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"onClick " + item.name + " " + position,Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"onLongClick " + item.name + " " + position,Toast.LENGTH_SHORT).show();
    }

    private fun setScrollListener() : RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val canScrollUp : Boolean = recyclerView.canScrollVertically(-1)
                val canScrollDown : Boolean = recyclerView.canScrollVertically(1)
                when {
                    canScrollUp && canScrollDown -> {
                        Log.d(TAG,"Recycler View at Middle")
                    }
                    canScrollDown && !canScrollUp -> {
                        Log.d(TAG,"Recycler View top reached")
                    }
                    canScrollUp && !canScrollDown -> {
                        Log.d(TAG,"Recycler View bottom reached")
                    }
                }

                when {
                    dy > 0 -> {
                        Log.d(TAG,"Recycler View Scrolling Down")
                    }
                    dy < 0 -> {
                        Log.d(TAG,"Recycler View Scrolling Up")
                    }
                }
            }
        }
    }
}
