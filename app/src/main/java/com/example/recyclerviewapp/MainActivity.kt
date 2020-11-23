package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomListeners {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecylerView()
        setItems()
    }

    private fun setRecylerView() {
        adapter = CustomAdapter(this@MainActivity, SwipeState.LEFT_RIGHT)
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
    }

    override fun onStart() {
        super.onStart()
        adapter.setItems(itemList)
    }
}