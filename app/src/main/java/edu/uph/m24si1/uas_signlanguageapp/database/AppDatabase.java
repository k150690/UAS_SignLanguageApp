package edu.uph.m24si1.uas_signlanguageapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.uph.m24si1.uas_signlanguageapp.database.Dao.StoreDao;

@Database(entities = {StoreItem.class, UserInventory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoreDao storeDao();
}