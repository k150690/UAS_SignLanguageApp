package edu.uph.m24si1.uas_signlanguageapp.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;

public class DatabaseInitializer {

    private static AppDatabase database;

    // Fungsi untuk memanggil atau membuat database di HP user
    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "sign_language_db")
                    // Menggunakan Callback untuk mengisi barang toko pertama kali pas database dibuat
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Mengisi data di background thread agar aplikasi tidak nge-lag/freeze
                            Executors.newSingleThreadExecutor().execute(() -> {
                                populateInitialData(database);
                            });
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    private static void populateInitialData(AppDatabase db) {
        if (db == null) return;

        // KEMBALI JADI 3 PARAMETER (Biar gak merah lagi)
        db.storeDao().insertItem(new StoreItem("Isyarat Master", "TITLE", 500));
        db.storeDao().insertItem(new StoreItem("Silent Hero", "TITLE", 300));

        // Contoh frame (Isinya 3 parameter: nama, tipe FRAME, dan harga)
        db.storeDao().insertItem(new StoreItem("Frame Tangan Emas", "FRAME", 1000));
        db.storeDao().insertItem(new StoreItem("Frame Kreator Isyarat", "FRAME", 750));
    }
}