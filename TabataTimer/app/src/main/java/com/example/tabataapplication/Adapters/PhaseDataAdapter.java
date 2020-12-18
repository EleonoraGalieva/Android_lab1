package com.example.tabataapplication.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabataapplication.Models.Phase;
import com.example.tabataapplication.R;

import java.util.List;

public class PhaseDataAdapter extends RecyclerView.Adapter<PhaseViewHolder> {
    private final LayoutInflater inflater;
    private final List<Phase> phases;

    public PhaseDataAdapter(Context context, List<Phase> phases) {
        this.inflater = LayoutInflater.from(context);
        this.phases = phases;
    }

    @NonNull
    @Override
    public PhaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.phase_item, parent, false);
        return new PhaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhaseViewHolder holder, int position) {
        final Phase phase = phases.get(position);
        holder.actionDescription.setText(phase.getDescription());
        holder.time.setText(String.valueOf(phase.getTime()));
        holder.actionName.setText(phase.getActionName());
        holder.actionImage.setImageDrawable(phase.getActionImage());
        holder.actionDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phase.setDescription(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return phases.size();
    }
}
