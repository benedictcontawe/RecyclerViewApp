package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener, View.OnKeyListener {

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>
    private var selectedItem : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        expandedArrowLeft.setOnClickListener(this)
        expandedArrowRight.setOnClickListener(this)
        expandedRecyclerView.setOnTouchListener(this)
        expandedRecyclerView.setOnTouchListener(this)
        //expandedRecyclerView.setOnKeyListener(this);

        setRecylerView()
        setItems()
    }

    private fun setRecylerView() {
        adapter = CustomAdapter(this)
        //expandedRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayout.VERTICAL,false));
        expandedRecyclerView.adapter = adapter
        expandedRecyclerView.setHasFixedSize(true)
    }

    private fun setItems() {
        itemList = ArrayList()
        itemList.add(CustomViewModel("A"))
        itemList.add(CustomViewModel("B"))
        itemList.add(CustomViewModel("C"))
        itemList.add(CustomViewModel("D"))
        itemList.add(CustomViewModel("E"))
        itemList.add(CustomViewModel("F"))
        itemList.add(CustomViewModel("G"))
    }

    override fun onStart() {
        super.onStart()
        adapter.setItems(itemList)
        selectedItem = 0
        expandedRecyclerView.scrollToPosition(selectedItem)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.expandedArrowLeft -> {
                if (selectedItem > 0) {
                    selectedItem--
                    Log.d("onClick", "Left $selectedItem")
                    expandedRecyclerView.smoothScrollToPosition(selectedItem)
                }
            }
            R.id.expandedArrowRight -> {
                if (selectedItem < adapter.itemCount) {
                    selectedItem++
                    Log.d("onClick", "Right $selectedItem")
                    expandedRecyclerView.smoothScrollToPosition(selectedItem)
                }
            }
            else -> {
                Log.d("onClick", "Default")
            }
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return if (view.id == R.id.expandedRecyclerView)
        {
            true
        }
        else {
            false
        }
    }

    override fun onKey(view: View, keyCode: Int, keyEvent: KeyEvent): Boolean {
        if (view.id == R.id.expandedRecyclerView) {
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> Log.d("onKey", "Up")
                KeyEvent.KEYCODE_DPAD_LEFT -> Log.d("onKey", "Left")
                KeyEvent.KEYCODE_DPAD_CENTER and KeyEvent.ACTION_UP -> Log.d("onKey", "KEYCODE_DPAD_CENTER, ACTION_UP")
                KeyEvent.KEYCODE_DPAD_CENTER and KeyEvent.ACTION_DOWN -> Log.d("onKey", "KEYCODE_DPAD_CENTER , ACTION_DOWN")
                KeyEvent.KEYCODE_DPAD_RIGHT -> Log.d("onKey", "Right")
                KeyEvent.KEYCODE_DPAD_DOWN -> Log.d("onKey", "Down")
            }
            return true
        } else {
            Log.d("onKey", "Default")
            return false
        }
    }
}
