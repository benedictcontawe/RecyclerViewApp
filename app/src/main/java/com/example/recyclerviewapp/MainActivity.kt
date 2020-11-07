package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomListeners {

    private lateinit var adapter : CustomAdapter
    private lateinit var itemTouchHelperCallback : CustomItemTouchHelperCallback
    private lateinit var itemTouchHelper : ItemTouchHelper
    private lateinit var itemList : MutableList<CustomViewModel>

    companion object {
        private var TAG : String = MainActivity::class.java.simpleName

        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecylerView()
        setItems()
        adapter.setItems(itemList)
    }

    private fun setRecylerView() {
        adapter = CustomAdapter(this@MainActivity, this@MainActivity)

        itemTouchHelperCallback = CustomItemTouchHelperCallback(adapter,true,true)
        itemTouchHelperCallback.setFadeOutSwipe(true)
        itemTouchHelperCallback.setTransparentDrag(true)

        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

        adapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        recycler_view.setLayoutManager(CustomLinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
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
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "H"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "I"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "J"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "K"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "L"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "M"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "N"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "O"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "P"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "Q"))
    }

    override fun onStart() {
        super.onStart()
        //adapter.setItems(itemList)
    }

    override fun onResume() {
        super.onResume()
        recycler_view.addOnScrollListener(setScrollListener())
        adapter.getItems().map {
            Log.e(TAG,"${it.name}")
        }
    }

    override fun onPause() {
        super.onPause()
        recycler_view.removeOnScrollListener(setScrollListener())
    }

    override fun onClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this@MainActivity,"onClick " + item.name + " " + position,Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this@MainActivity,"onLongClick " + item.name + " " + position,Toast.LENGTH_SHORT).show();
    }

    override fun onItemMoved(item  : CustomViewModel, fromPosition : Int, toPosition : Int) {
        //Toast.makeText(this@MainActivity,"onItemMoved ${item.name} $fromPosition $toPosition",Toast.LENGTH_SHORT).show()
        Log.d(TAG,"onItemMoved ${item.name} $fromPosition $toPosition")
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
