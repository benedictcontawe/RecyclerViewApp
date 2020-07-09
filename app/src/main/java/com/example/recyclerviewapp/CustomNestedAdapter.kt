package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomNestedAdapter : RecyclerView.Adapter<BaseNestedViewHolder> {

    companion object {
        private var TAG : String = CustomNestedAdapter::class.java.simpleName

        private const val HorizontalView = 0
        private const val VerticalView = 1
    }
    /**Main */
    private lateinit var customListeners : CustomListeners

    private lateinit var horizontalList : MutableList<CustomViewModel>
    private lateinit var verticalList : MutableList<CustomViewModel>

    constructor(customListeners : CustomListeners) : super() {
        this.customListeners = customListeners
    }

    init {
        horizontalList = mutableListOf()
        verticalList = mutableListOf()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseNestedViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View
        when(viewType) {
            HorizontalView -> {
                view = layoutInflater.inflate(R.layout.list_horizontal_sample, parent, false)
                return CustomNestedHorizontalViewHolder(parent.getContext(), view)
            }
            VerticalView -> {
                view = layoutInflater.inflate(R.layout.list_vertical_sample, parent, false)
                return CustomNestedVerticalViewHolder(parent.getContext(), view)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.list_vertical_sample, parent, false)
                return CustomNestedVerticalViewHolder(parent.getContext(), view)
            }
        }
    }

    override fun onBindViewHolder(holder : BaseNestedViewHolder, position : Int) {
        when(getItemViewType(position)) {
            HorizontalView -> {
                holder.bindDataToViewHolder(horizontalList)
            }
            VerticalView -> {
                holder.bindDataToViewHolder(verticalList)
            }
        }

    }

    override fun getItemCount() : Int {
        return 2
    }

    override fun getItemViewType(position : Int) : Int {
        return when(position) {
            0 -> HorizontalView
            else -> VerticalView
        }
    }

    public fun setHorizontalItems(items : MutableList<CustomViewModel>) {
        horizontalList.clear()
        horizontalList.addAll(items)
        notifyDataSetChanged()
    }

    public fun setVerticalItems(items : MutableList<CustomViewModel>) {
        verticalList.clear()
        verticalList.addAll(items)
        notifyDataSetChanged()
    }
}