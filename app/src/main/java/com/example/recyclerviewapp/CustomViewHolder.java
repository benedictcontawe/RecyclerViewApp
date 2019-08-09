package com.example.recyclerviewapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class CustomViewHolder extends RecyclerView.ViewHolder {

    /**Main*/
    private Context context;
    private CustomListeners customListeners;
    /**Data*/
    private ImageView imageView;
    private TextView textView;
    /**With Events and Others*/
    private CardView cardView;

    public CustomViewHolder(@NonNull Context context, @NonNull View itemView,CustomListeners customListeners) {
        super(itemView);

        this.context = context;
        this.customListeners = customListeners;
        this.cardView = itemView.findViewById(R.id.card_view);
        this.imageView = itemView.findViewById(R.id.image_view);
        this.textView = itemView.findViewById(R.id.text_view);
    }

    public void bindDataToViewHolder(final CustomViewModel item, final int position) {
        //region Input Data
        imageView.setBackgroundResource(item.icons);
        textView.setText(item.names);
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListeners.onClick(item, position);
            }
        });
        /* On Long Click */
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                customListeners.onLongClick(item, position);
                return false;
            }
        });
        //endregion
    }

}
