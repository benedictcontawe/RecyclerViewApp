package com.example.recyclerviewapp;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    /**Main*/
    /**Data*/
    private TextView textView;

    public CustomViewHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.text_view);
    }

    public void bindDataToViewHolder(final CustomViewModel item, final int position) {
        //region Input Data
        textView.setText(item.names);
        //endregion
    }
}
