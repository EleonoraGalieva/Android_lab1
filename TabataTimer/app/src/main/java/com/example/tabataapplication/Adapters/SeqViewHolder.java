package com.example.tabataapplication.Adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SeqViewHolder extends RecyclerView.ViewHolder {
    final TextView seqTitle;
    final CoordinatorLayout seqItemLayout;
    final FloatingActionButton fabEdit;

    public SeqViewHolder(@NonNull View itemView) {
        super(itemView);
        seqTitle = itemView.findViewById(R.id.seqTitle);
        seqItemLayout = itemView.findViewById(R.id.seqItemLayout);
        fabEdit = itemView.findViewById(R.id.fabEdit);
    }
}
