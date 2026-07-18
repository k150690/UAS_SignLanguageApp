package edu.uph.m24si1.uas_signlanguageapp.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.uph.m24si1.uas_signlanguageapp.R;
import edu.uph.m24si1.uas_signlanguageapp.database.StoreItem;
import edu.uph.m24si1.uas_signlanguageapp.database.UserInventory;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<StoreItem> storeItems;
    private List<UserInventory> userInventories;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onActionClick(StoreItem item, UserInventory inventory);
    }

    public StoreAdapter(List<StoreItem> storeItems, List<UserInventory> userInventories, OnItemClickListener listener) {
        this.storeItems = storeItems;
        this.userInventories = userInventories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreItem currentItem = storeItems.get(position);

        UserInventory currentInventory = null;
        for (UserInventory inv : userInventories) {
            if (inv.itemId.equals(currentItem.itemId)) {
                currentInventory = inv;
                break;
            }
        }

        holder.tvItemName.setText(currentItem.itemName);
        holder.tvItemType.setText(currentItem.itemType);

        if (currentInventory == null || !currentInventory.isOwned) {
            holder.btnAction.setText(currentItem.itemPrice + " Koin");
            holder.btnAction.setEnabled(true);
            holder.btnAction.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1E3A8A"))); // Warna Biru
            holder.btnAction.setTextColor(Color.WHITE);
        } else if (!currentInventory.isEquipped) {
            holder.btnAction.setText("Pakai");
            holder.btnAction.setEnabled(true);
            holder.btnAction.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8B5A2B"))); // Warna Coklat
            holder.btnAction.setTextColor(Color.WHITE);
        } else {
            holder.btnAction.setText("Dipakai");
            holder.btnAction.setEnabled(false);
            holder.btnAction.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            holder.btnAction.setTextColor(Color.DKGRAY);
        }

        UserInventory finalCurrentInventory = currentInventory;
        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionClick(currentItem, finalCurrentInventory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItems != null ? storeItems.size() : 0;
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemType;
        Button btnAction;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}