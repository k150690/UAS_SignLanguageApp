package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_inventory")
public class UserInventory {

    @PrimaryKey
    public int itemId; // Disamakan dengan itemId dari StoreItem di atas

    public boolean isOwned;    // true = sudah dibeli, false = belum punya
    public boolean isEquipped; // true = sedang dipasang di profil, false = tidak dipakai

    // Konstruktor untuk mencatat status barang user
    public UserInventory(int itemId, boolean isOwned, boolean isEquipped) {
        this.itemId = itemId;
        this.isOwned = isOwned;
        this.isEquipped = isEquipped;
    }
}