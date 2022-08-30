package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.MainBinder

class MainActivity : AppCompatActivity(), CustomListeners {

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()
        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    private var binder : MainBinder? = null
    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomHolderModel>

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        setRecylerView()
        setItems()
    }

    private fun setRecylerView() {
        adapter = CustomAdapter(this@MainActivity, SwipeState.LEFT_RIGHT)
        binder?.recyclerView?.setLayoutManager(CustomLinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false))
        binder?.recyclerView?.setAdapter(adapter)
        binder?.recyclerView?.setHasFixedSize(true)
    }

    private fun setItems() {
        itemList = mutableListOf<CustomHolderModel>()
        itemList.clear()
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "A"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "B"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "C"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "D"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "E"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "F"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "G"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "H"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "I"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "J"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "K"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "L"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "M"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "N"))
        itemList.add(CustomHolderModel(R.drawable.ic_person_white, "O"))
    }

    override fun onStart() {
        super.onStart()
        adapter.setItems(itemList)
    }

    override fun onClickLeft(item : CustomHolderModel?, position : Int) {
        Toast.makeText(this@MainActivity,"Left Arrow Clicked! $position",Toast.LENGTH_SHORT).show()
    }

    override fun onClickRight(item : CustomHolderModel?, position : Int) {
        Toast.makeText(this@MainActivity,"Right Arrow Clicked! $position",Toast.LENGTH_SHORT).show()
    }
}