package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_items")
public class StoreItem {

    @PrimaryKey(autoGenerate = true)
    public int itemId; // Nomor unik barang otomatis (1, 2, 3, dst)

    public String itemName; // Nama badge/title (Contoh: "Isyarat Master")
    public String itemType; // Jenisnya: "TITLE" atau "BADGE"
    public int price;       // Harga koinnya (Contoh: 200)

    // Konstruktor untuk membuat barang baru
    public StoreItem(String itemName, String itemType, int price) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
    }
}