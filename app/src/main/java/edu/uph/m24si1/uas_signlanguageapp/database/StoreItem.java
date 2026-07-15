package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_items")
public class StoreItem {

    @PrimaryKey(autoGenerate = true)
    public int itemId;

    public String itemName;
    public String itemType;
    public int price;

    public StoreItem(String itemName, String itemType, int price) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
    }
}