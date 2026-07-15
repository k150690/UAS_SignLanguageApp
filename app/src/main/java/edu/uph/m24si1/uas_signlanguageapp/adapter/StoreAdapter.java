package edu.uph.m24si1.uas_signlanguageapp.adapter;

import static android.os.Build.VERSION_CODES_FULL.R;

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

    private List<StoreItem> storeItemList;
    private List<UserInventory> userInventoryList;
    private OnItemClickListener listener;

    // Interface untuk mendeteksi klik tombol di halaman Activity nanti
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreItem item = storeItemList.get(position);

        // Cari apakah user sudah memiliki barang ini di inventory-nya
        UserInventory currentInventory = null;
        for (UserInventory inv : userInventoryList) {
            if (inv.itemId == item.itemId) {
                currentInventory = inv;
                break;
            }
        }

        // Tampilkan nama dan tipe barang ke layar
        holder.tvItemName.setText(item.itemName);
        holder.tvItemType.setText("Tipe: " + item.itemType);

        // LOGIKA TOMBOL TOKO (Kunci dari fitur tokomu)
        if (currentInventory == null || !currentInventory.isOwned) {
            // Kondisi 1: Belum dibeli
            holder.btnAction.setText("Beli (" + item.price + ")");
            holder.btnAction.setEnabled(true);
        } else if (currentInventory.isOwned && !currentInventory.isEquipped) {
            // Kondisi 2: Sudah dibeli, tapi belum dipakai
            holder.btnAction.setText("Equip");
            holder.btnAction.setEnabled(true);
        } else if (currentInventory.isEquipped) {
            // Kondisi 3: Sedang dipakai
            holder.btnAction.setText("Dipakai");
            holder.btnAction.setEnabled(false); // Tombol mati karena sedang aktif digunakan
        }

        // Kirim data barang yang diklik ke halaman Activity
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

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}