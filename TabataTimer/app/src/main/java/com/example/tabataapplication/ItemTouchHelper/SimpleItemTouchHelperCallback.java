package com.example.tabataapplication.ItemTouchHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapter itemAdapter;

    public SimpleItemTouchHelperCallback(
            ItemTouchHelperAdapter adapter) {
        itemAdapter = adapter;
    }

    //to allow dragging both ways
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        itemAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        itemAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    //to allow dragging items
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //to allow swiping items
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
