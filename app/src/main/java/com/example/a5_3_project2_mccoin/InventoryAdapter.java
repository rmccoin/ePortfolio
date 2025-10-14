package com.example.a5_3_project2_mccoin;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private Context context;
    private ArrayList<inventoryitem> items;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(inventoryitem item);
        void onDelete(inventoryitem item);
    }

    public InventoryAdapter(Context context, ArrayList<inventoryitem> items, OnItemActionListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        inventoryitem item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText("Qty: " + item.getQuantity());
        holder.editButton.setOnClickListener(v -> listener.onEdit(item));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity;
        Button editButton, deleteButton;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
