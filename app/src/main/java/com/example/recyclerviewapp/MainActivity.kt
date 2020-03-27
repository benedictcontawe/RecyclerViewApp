package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomListeners {

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>

    companion object {
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
        adapter = CustomAdapter(this)
        recycler_view.setLayoutManager(CustomLinearLayoutManager(this, LinearLayout.VERTICAL, false))
        recycler_view.setAdapter(adapter)
        recycler_view.setHasFixedSize(true)

        recycler_view.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("MainActivity","ACTION_DOWN")
                        return false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.d("MainActivity","ACTION_MOVE")
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("MainActivity","ACTION_UP")
                        adapter.resetViews()
                        Toast.makeText(baseContext,"on Release Touch",Toast.LENGTH_SHORT).show()
                        return false
                    }
                }
                return false
            }
        })
    }

    private fun setItems() {
        itemList = mutableListOf<CustomViewModel>()
        itemList.clear()
        itemList.add(CustomViewModel(0,R.drawable.ic_person_white, "A"))
        itemList.add(CustomViewModel(1,R.drawable.ic_launcher_foreground, "B"))
        itemList.add(CustomViewModel(2,R.drawable.ic_person_white, "C",CustomViewModel.DefaultViewType))
        itemList.add(CustomViewModel(3,R.drawable.ic_person_white, "D",CustomViewModel.DefaultViewType))
        itemList.add(CustomViewModel(4,R.drawable.ic_person_white, "E",CustomViewModel.DefaultViewType))
        itemList.add(CustomViewModel(5,R.drawable.ic_launcher_foreground, "F",CustomViewModel.DefaultViewType))
        itemList.add(CustomViewModel(6,R.drawable.ic_person_white, "G",CustomViewModel.DefaultViewType))
    }

    override fun onResume() {
        super.onResume()
        adapter.setItems(itemList)
    }

    override fun onPause() {
        super.onPause()
        adapter.deleteAllItems()
    }

    override fun onClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"on Click",Toast.LENGTH_SHORT).show()
        when(item.viewType) {
            CustomViewModel.DefaultViewType -> {
                adapter.changeView(CustomViewModel.IconViewType, position)
            }
            CustomViewModel.IconViewType -> {
                adapter.changeView(CustomViewModel.DefaultViewType, position)
            }
            CustomViewModel.NameViewType -> {
                adapter.changeView(CustomViewModel.DefaultViewType, position)
            }
            else -> {
                Toast.makeText(this,"View Type Cannot be determined",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLongClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"on Long Click",Toast.LENGTH_SHORT).show()
        when(item.viewType) {
            CustomViewModel.DefaultViewType -> {
                adapter.changeView(CustomViewModel.NameViewType, position)
            }
            CustomViewModel.IconViewType -> {
                adapter.changeView(CustomViewModel.DefaultViewType, position)
            }
            CustomViewModel.NameViewType -> {
                adapter.changeView(CustomViewModel.DefaultViewType, position)
            }
            else -> {
                Toast.makeText(this,"View Type Cannot be determined",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.deleteAllItems()
    }
}
