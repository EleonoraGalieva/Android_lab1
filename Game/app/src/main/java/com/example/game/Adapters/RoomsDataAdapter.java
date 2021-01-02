package com.example.game.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.Models.Room;
import com.example.game.R;

import java.util.List;

public class RoomsDataAdapter extends RecyclerView.Adapter<RoomsViewHolder> {
    public interface OnRoomClickListener {
        void onRoomClick(Room room, int position);
    }

    private final RoomsDataAdapter.OnRoomClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<Room> rooms;

    public RoomsDataAdapter(Context context, List<Room> rooms, OnRoomClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.room_item, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, int position) {
        final Room room = rooms.get(position);
        holder.roomName.setText(room.getRoomName());
        holder.itemView.setOnClickListener(view -> onClickListener.onRoomClick(room, position));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
