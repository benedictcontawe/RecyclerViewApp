package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        adapter = CustomAdapter(this, this)
        recycler_view.setLayoutManager(CustomLinearLayoutManager(this, LinearLayout.VERTICAL, false))
        recycler_view.setAdapter(adapter)
        recycler_view.setHasFixedSize(true)
    }

    private fun setItems() {
        itemList = mutableListOf<CustomViewModel>()
        itemList.clear()
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "A"))
        itemList.add(CustomViewModel(R.drawable.ic_launcher_foreground, "B"))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "C",CustomViewModel.IconViewType))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "D",CustomViewModel.IconViewType))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "E",CustomViewModel.NameViewType))
        itemList.add(CustomViewModel(R.drawable.ic_launcher_foreground, "F",CustomViewModel.NameViewType))
        itemList.add(CustomViewModel(R.drawable.ic_person_white, "G",CustomViewModel.NameViewType))
    }

    override fun onStart() {
        super.onStart()
        adapter.setItems(itemList)
    }

    override fun onClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"onClick " + item.name + " " + position,Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(item: CustomViewModel, position: Int) {
        Toast.makeText(this,"onLongClick " + item.name + " " + position,Toast.LENGTH_SHORT).show();
    }
}
