package edu.uph.m24si1.uas_signlanguageapp.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.uph.m24si1.uas_signlanguageapp.database.StoreItem;
import edu.uph.m24si1.uas_signlanguageapp.database.UserInventory;

@Dao
public interface StoreDao {
    @Query("SELECT * FROM store_items")
    List<StoreItem> getAllStoreItems();

    // Mengambil inventory HANYA milik user yang sedang login
    @Query("SELECT * FROM user_inventory WHERE ownerEmail = :email")
    List<UserInventory> getUserInventory(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void buyItem(UserInventory inventory);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStoreItems(List<StoreItem> items);

    // Mencopot semua barang dari tipe tertentu untuk user tertentu (Mencegah pakai 2 title)
    @Query("UPDATE user_inventory SET isEquipped = 0 WHERE ownerEmail = :email AND itemId IN (SELECT itemId FROM store_items WHERE itemType = :type)")
    void unequipAllItemsOfType(String type, String email);

    // Memasang barang spesifik untuk user tertentu
    @Query("UPDATE user_inventory SET isEquipped = 1 WHERE itemId = :id AND ownerEmail = :email")
    void equipItem(String id, String email);
}