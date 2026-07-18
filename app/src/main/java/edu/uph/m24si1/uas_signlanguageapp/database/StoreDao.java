package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StoreDao {
    @Query("SELECT * FROM store_items")
    List<StoreItem> getAllStoreItems();

    @Query("SELECT * FROM user_inventory WHERE ownerEmail = :email")
    List<UserInventory> getUserInventory(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void buyItem(UserInventory inventory);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStoreItems(List<StoreItem> items);

    @Query("UPDATE user_inventory SET isEquipped = 0 WHERE ownerEmail = :email AND itemId IN (SELECT itemId FROM store_items WHERE itemType = :type)")
    void unequipAllItemsOfType(String type, String email);

    @Query("UPDATE user_inventory SET isEquipped = 1 WHERE itemId = :id AND ownerEmail = :email")
    void equipItem(String id, String email);
}