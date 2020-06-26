package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomListeners {

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>

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
        adapter = CustomAdapter(this, this)
        recycler_view.setLayoutManager(CustomLinearLayoutManager(this, LinearLayout.VERTICAL, false))
        recycler_view.setAdapter(adapter)
        recycler_view.setHasFixedSize(true)
    }

    private fun setItems() {
        itemList = mutableListOf<CustomViewModel>()
        itemList.clear()
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "A"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "B"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "C"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "D"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "E"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "F"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "G"))
    }

    override fun onStart() {
        super.onStart()
        adapter.setItems(itemList)
    }

    override fun onResume() {
        super.onResume()
        recycler_view.addOnScrollListener(setScrollListener())
    }

    override fun onPause() {
        super.onPause()
        recycler_view.removeOnScrollListener(setScrollListener())
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
