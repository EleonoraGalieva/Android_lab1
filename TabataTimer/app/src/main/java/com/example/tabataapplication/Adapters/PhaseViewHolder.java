package com.example.tabataapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.R;

public class PhaseViewHolder extends RecyclerView.ViewHolder {
    final ImageView actionImage;
    final TextView actionName;
    final TextView time;
    final TextView actionDescription;

    public PhaseViewHolder(@NonNull View itemView) {
        super(itemView);
        actionImage = itemView.findViewById(R.id.actionImage);
        actionName = itemView.findViewById(R.id.actionName);
        time = itemView.findViewById(R.id.time);
        actionDescription = itemView.findViewById(R.id.actionDescription);
    }
}
