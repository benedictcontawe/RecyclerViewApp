package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
        adapter = CustomAdapter(this, this)
        recycler_view.setLayoutManager(CustomLinearLayoutManager(this, LinearLayout.VERTICAL, false))
        recycler_view.setAdapter(adapter)
        recycler_view.setHasFixedSize(true)

        adapter.setActivity(this)
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
}
