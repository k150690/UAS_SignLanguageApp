package edu.uph.m24si1.uas_signlanguageapp.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.uph.m24si1.uas_signlanguageapp.database.StoreItem;
import edu.uph.m24si1.uas_signlanguageapp.database.UserInventory;

import java.util.List;

@Dao
public interface StoreDao {

    // 1. Perintah untuk memasukkan barang dagangan pertama kali ke toko
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(StoreItem item);

    // 2. Perintah untuk mengambil semua barang di toko buat dipajang di layar HP
    @Query("SELECT * FROM store_items")
    List<StoreItem> getAllStoreItems();

    // 3. Perintah untuk menyimpan status kepemilikan pas user BERHASIL BELI
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void buyItem(UserInventory inventory);

    // 4. Perintah untuk mengecek barang apa saja yang sudah dibeli oleh user
    @Query("SELECT * FROM user_inventory")
    List<UserInventory> getUserInventory();

    // 5. Perintah untuk mencopot (unequip) semua title lama yang sedang dipakai
    // Biar user gak bisa pakai 2 title sekaligus di profilnya
    @Query("UPDATE user_inventory SET isEquipped = 0 WHERE itemId IN (SELECT itemId FROM store_items WHERE itemType = :type)")
    void unequipAllItemsOfType(String type);

    // 6. Perintah untuk memasang (equip) title/badge yang baru dipilih user
    @Query("UPDATE user_inventory SET isEquipped = 1 WHERE itemId = :id")
    void equipItem(int id);
}