package com.example.recyclerviewapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class CustomViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private CustomListeners customListeners;
    private CardView cardView;
    private ImageView imageView;
    private TextView textView;

    public CustomViewHolder(@NonNull Context context, @NonNull View itemView,CustomListeners customListeners) {
        super(itemView);

        this.context = context;

        this.customListeners = customListeners;

        this.cardView = itemView.findViewById(R.id.card_view);
        this.imageView = itemView.findViewById(R.id.image_view);
        this.textView = itemView.findViewById(R.id.text_view);
    }

    public void bindDataToViewHolder(CustomViewModel item, int position) {
        imageView.setBackgroundResource(item.icons);
        textView.setText(item.names);
    }

}
