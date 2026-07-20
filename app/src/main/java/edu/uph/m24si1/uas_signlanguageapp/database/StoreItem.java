package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_items")
public class StoreItem {
    @PrimaryKey
    @NonNull
    public String itemId;
    public String itemName;
    public String itemType;
    public int itemPrice;

    public StoreItem(@NonNull String itemId, String itemName, String itemType, int itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
    }
}