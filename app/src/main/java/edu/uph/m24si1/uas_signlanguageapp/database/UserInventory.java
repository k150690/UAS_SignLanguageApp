package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "user_inventory", primaryKeys = {"itemId", "ownerEmail"})
public class UserInventory {
    @NonNull
    public String itemId;

    @NonNull
    public String ownerEmail;

    public boolean isOwned;
    public boolean isEquipped;

    public UserInventory(@NonNull String itemId, @NonNull String ownerEmail, boolean isOwned, boolean isEquipped) {
        this.itemId = itemId;
        this.ownerEmail = ownerEmail;
        this.isOwned = isOwned;
        this.isEquipped = isEquipped;
    }
}