package edu.uph.m24si1.uas_signlanguageapp.database;

import android.content.Context;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseInitializer {
    private static AppDatabase instance;

    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "signteach_database")
                    .fallbackToDestructiveMigration() // Reset database otomatis jika versi berubah
                    .build();

            Executors.newSingleThreadExecutor().execute(() -> {
                if (instance.storeDao().getAllStoreItems().isEmpty()) {
                    List<StoreItem> defaultItems = new ArrayList<>();
                    defaultItems.add(new StoreItem("title_1", "Pemula", "TITLE", 0));
                    defaultItems.add(new StoreItem("title_2", "NPC Berbakat", "TITLE", 50));
                    defaultItems.add(new StoreItem("title_3", "Pro Player", "TITLE", 150));
                    defaultItems.add(new StoreItem("frame_1", "Frame Dasar", "FRAME", 0));
                    defaultItems.add(new StoreItem("frame_2", "Frame Emas", "FRAME", 200));

                    instance.storeDao().insertStoreItems(defaultItems);
                }
            });
        }
        return instance;
    }
}