package edu.uph.m24si1.uas_signlanguageapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.uph.m24si1.uas_signlanguageapp.R;
import edu.uph.m24si1.uas_signlanguageapp.database.StoreItem;
import edu.uph.m24si1.uas_signlanguageapp.database.UserInventory;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private final List<StoreItem> storeItemList;
    private final List<UserInventory> userInventoryList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onActionClick(StoreItem item, UserInventory inventory);
    }

    public StoreAdapter(List<StoreItem> storeItemList, List<UserInventory> userInventoryList, OnItemClickListener listener) {
        this.storeItemList = storeItemList;
        this.userInventoryList = userInventoryList;
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
        StoreItem item = storeItemList.get(position);

        UserInventory currentInventory = null;
        for (UserInventory inv : userInventoryList) {
            // Note: Jika di Room kelompokmu itemId adalah String, ganti '==' jadi '.equals(item.itemId)'
            if (inv.itemId == item.itemId) {
                currentInventory = inv;
                break;
            }
        }

        holder.tvItemName.setText(item.itemName);
        holder.tvItemType.setText(item.itemType);

        // ====================================================================
        // LOGIKA KUSTOMISASI BARU: Sembunyikan gambar jika TITLE, munculkan jika FRAME
        // ====================================================================
        if (item.itemType != null && item.itemType.equalsIgnoreCase("TITLE")) {
            holder.imgItemIcon.setVisibility(View.GONE); // Gambar hilang total, teks mepet ke kiri
        } else {
            holder.imgItemIcon.setVisibility(View.VISIBLE); // Gambar muncul normal untuk tipe FRAME
        }

        if (currentInventory == null || !currentInventory.isOwned) {
            holder.btnAction.setText("Beli");
            holder.btnAction.setEnabled(true);
        } else if (!currentInventory.isEquipped) {
            holder.btnAction.setText("Equip");
            holder.btnAction.setEnabled(true);
        } else {
            holder.btnAction.setText("Dipakai");
            holder.btnAction.setEnabled(false);
        }

        UserInventory finalInventory = currentInventory;
        holder.btnAction.setOnClickListener(v -> listener.onActionClick(item, finalInventory));
    }

    @Override
    public int getItemCount() {
        return storeItemList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemType;
        Button btnAction;
        View imgItemIcon; // TAMBAHAN: Variabel untuk komponen gambar ikon

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            btnAction = itemView.findViewById(R.id.btnAction);
            imgItemIcon = itemView.findViewById(R.id.imgItemIcon); // TAMBAHAN: Hubungkan ke ID XML
        }
    }
}