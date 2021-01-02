package com.example.game.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.Models.User;
import com.example.game.R;

import java.util.List;

public class UsersDataAdapter extends RecyclerView.Adapter<UsersViewHolder> {
    public interface OnUserClickListener {
        void onUserClick(User user, int position);
    }

    private final OnUserClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<User> users;

    public UsersDataAdapter(Context context, List<User> users, OnUserClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        final User user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onUserClick(user, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
