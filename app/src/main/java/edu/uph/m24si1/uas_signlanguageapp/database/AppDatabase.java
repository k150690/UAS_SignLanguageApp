package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import edu.uph.m24si1.uas_signlanguageapp.database.Dao.StoreDao;

// Mendaftarkan tabel StoreItem dan UserInventory ke dalam database sistem
@Database(entities = {StoreItem.class, UserInventory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // Menyediakan tangan pengambil data (DAO) agar bisa dipakai di halaman toko
    public abstract StoreDao storeDao();
}