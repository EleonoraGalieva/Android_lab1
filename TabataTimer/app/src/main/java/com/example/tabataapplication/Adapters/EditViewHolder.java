package com.example.tabataapplication.Adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditViewHolder extends RecyclerView.ViewHolder {
    final ImageView editImage;
    final TextView editActionName;
    final EditText editDescription;
    final TextView editTime;
    final FloatingActionButton fabDecrease;
    final FloatingActionButton fabIncrease;

    public EditViewHolder(@NonNull View itemView) {
        super(itemView);
        editImage = itemView.findViewById(R.id.editImage);
        editActionName = itemView.findViewById(R.id.editActionName);
        editTime = itemView.findViewById(R.id.editTime);
        editDescription = itemView.findViewById(R.id.editDescription);
        fabDecrease = itemView.findViewById(R.id.fabDecrease);
        fabIncrease = itemView.findViewById(R.id.fabIncrease);
    }
}
