package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_inventory")
public class UserInventory {

    @PrimaryKey
    public int itemId;

    public boolean isOwned;
    public boolean isEquipped;

    public UserInventory(int itemId, boolean isOwned, boolean isEquipped) {
        this.itemId = itemId;
        this.isOwned = isOwned;
        this.isEquipped = isEquipped;
    }
}