package com.example.recyclerviewapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.MainBinder
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity(), CustomListeners {

    private lateinit var binder : MainBinder
    private val adapter : CustomAdapter by lazy { CustomAdapter(this@MainActivity) }
    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()

        fun newIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        //viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
        binder.setViewModel(viewModel)
        binder.setLifecycleOwner(this@MainActivity)
        setRecylerView()
        Coroutines.main(this@MainActivity, {
            viewModel.getFlowItems().collectLatest ( action = { pagingDateList ->
                Log.d(TAG, "getFlowItems collectLatest ${pagingDateList}")
                adapter.submitData(lifecycle,pagingDateList)
            } )
        })
    }

    private fun setRecylerView() {
        //adapter = CustomAdapter(this@MainActivity)
        binder.recyclerView.setLayoutManager(CustomLinearLayoutManager(this, LinearLayout.VERTICAL, false))
        binder.recyclerView.setAdapter(adapter)
        binder.recyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        //adapter.setItems(viewModel.getItems())
        //adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        binder.recyclerView.addOnScrollListener(setScrollListener())
    }

    override fun onPause() {
        super.onPause()
        binder.recyclerView.removeOnScrollListener(setScrollListener())
    }

    override fun onClick(item : CustomModel?, position : Int) {
        Log.d(TAG, "adapter getItemCount ${adapter.getItemCount()}")
        Toast.makeText(this@MainActivity,"onClick " + item?.name + " " + position,Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(item : CustomModel?, position : Int) {
        Toast.makeText(this@MainActivity,"onLongClick " + item?.name + " " + position,Toast.LENGTH_SHORT).show();
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

    override fun onDestroy() {
        super.onDestroy()
    }
}