package com.example.tabataapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.EditActivity;
import com.example.tabataapplication.ItemTouchHelper.ItemTouchHelperAdapter;
import com.example.tabataapplication.R;

import com.example.tabataapplication.Models.Sequence;
import com.example.tabataapplication.TimerActivity;

import java.util.Collections;
import java.util.List;

public class SeqDataAdapter extends RecyclerView.Adapter<SeqViewHolder>
        implements ItemTouchHelperAdapter {

    private final LayoutInflater inflater;
    private final List<Sequence> sequences;
    private final Context context;

    public SeqDataAdapter(Context context, List<Sequence> sequences) {
        this.inflater = LayoutInflater.from(context);
        this.sequences = sequences;
        this.context = context;
    }

    @NonNull
    @Override
    public SeqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.seq_item, parent, false);
        return new SeqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeqViewHolder holder, int position) {
        final Sequence sequence = sequences.get(position);
        holder.seqTitle.setText(sequence.getTitle());
        holder.seqItemLayout.setBackgroundColor(sequence.getColour());
        holder.seqItemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(inflater.getContext(), TimerActivity.class);
            intent.putExtra("idSeq", sequence.getId());
            inflater.getContext().startActivity(intent);
        });
        holder.fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(inflater.getContext(), EditActivity.class);
            intent.putExtra("idSeq", sequence.getId());
            inflater.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sequences.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(sequences, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(sequences, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        final Sequence sequence = sequences.get(position);
        sequences.remove(position);
        Intent intent = new Intent("sequence");
        intent.putExtra("sequence-id", sequence.getId());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyItemRemoved(position);
    }
}

