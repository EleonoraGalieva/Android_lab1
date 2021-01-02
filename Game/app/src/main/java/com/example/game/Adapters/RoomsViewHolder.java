package com.example.game.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.R;

public class RoomsViewHolder extends RecyclerView.ViewHolder {
    final TextView roomName;

    public RoomsViewHolder(@NonNull View itemView) {
        super(itemView);
        roomName = itemView.findViewById(R.id.roomName);
    }
}
