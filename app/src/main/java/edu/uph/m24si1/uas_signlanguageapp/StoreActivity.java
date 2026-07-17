package edu.uph.m24si1.uas_signlanguageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.uph.m24si1.uas_signlanguageapp.adapter.StoreAdapter;
import edu.uph.m24si1.uas_signlanguageapp.database.AppDatabase;
import edu.uph.m24si1.uas_signlanguageapp.database.DatabaseInitializer;
import edu.uph.m24si1.uas_signlanguageapp.database.StoreItem;
import edu.uph.m24si1.uas_signlanguageapp.database.UserInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView rvStoreItems;
    private StoreAdapter adapter;
    private AppDatabase db;

    private List<StoreItem> storeItemList = new ArrayList<>();
    private List<UserInventory> userInventoryList = new ArrayList<>();

    // 1. TAMBAHAN: Variabel global untuk Tab Kategori
    private Button btnTabTitle, btnTabFrame;
    private String kategoriAktif = "TITLE"; // Default awal menampilkan TITLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // Inisialisasi Database Room
        db = DatabaseInitializer.getDatabase(this);

        // Hubungkan RecyclerView dengan layout XML
        rvStoreItems = findViewById(R.id.rvStoreItems);
        rvStoreItems.setLayoutManager(new LinearLayoutManager(this));

        // 2. TAMBAHAN: Hubungkan variabel tombol tab dengan ID di XML
        btnTabTitle = findViewById(R.id.btnTabTitle);
        btnTabFrame = findViewById(R.id.btnTabFrame);

        // 3. TAMBAHAN: Logika ketika Tab Title ditekan
        btnTabTitle.setOnClickListener(v -> {
            kategoriAktif = "TITLE";
            // Ubah tombol Title jadi Biru (Aktif), tombol Frame jadi Cream (Tidak Aktif)
            btnTabTitle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            btnTabTitle.setTextColor(getResources().getColor(R.color.white));

            btnTabFrame.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.cream)));
            btnTabFrame.setTextColor(getResources().getColor(R.color.blue));

            loadDataDariDatabase(); // Refresh list agar data ter-filter
        });

        // 4. TAMBAHAN: Logika ketika Tab Frame ditekan
        btnTabFrame.setOnClickListener(v -> {
            kategoriAktif = "FRAME";
            // Ubah tombol Frame jadi Biru (Aktif), tombol Title jadi Cream (Tidak Aktif)
            btnTabFrame.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            btnTabFrame.setTextColor(getResources().getColor(R.color.white));

            btnTabTitle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.cream)));
            btnTabTitle.setTextColor(getResources().getColor(R.color.blue));

            loadDataDariDatabase(); // Refresh list agar data ter-filter
        });

        // Pasang Adapter ke RecyclerView beserta Logika Klik Tombolnya
        adapter = new StoreAdapter(storeItemList, userInventoryList, new StoreAdapter.OnItemClickListener() {
            @Override
            public void onActionClick(StoreItem item, UserInventory inventory) {
                if (inventory == null || !inventory.isOwned) {
                    // KONDISI: User menekan tombol BELI
                    prosesBeliItem(item);
                } else if (!inventory.isEquipped) {
                    // KONDISI: User menekan tombol EQUIP
                    prosesEquipItem(item);
                }
            }
        });
        rvStoreItems.setAdapter(adapter);

        // Ambil data dari database untuk ditampilkan ke layar
        loadDataDariDatabase();
    }

    private void loadDataDariDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Mengambil semua data asli dari Room Database kelompokmu
            List<StoreItem> items = db.storeDao().getAllStoreItems();
            List<UserInventory> inventory = db.storeDao().getUserInventory();

            // Saring murni menggunakan kategori data Room yang aktif
            List<StoreItem> filteredItems = new ArrayList<>();
            if (items != null) {
                for (StoreItem item : items) {
                    if (item.itemType != null && item.itemType.equalsIgnoreCase(kategoriAktif)) {
                        filteredItems.add(item);
                    }
                }
            }

            runOnUiThread(() -> {
                storeItemList.clear();
                storeItemList.addAll(filteredItems);
                userInventoryList.clear();
                if (inventory != null) {
                    userInventoryList.addAll(inventory);
                }
                adapter.notifyDataSetChanged();

                // Update saldo koin dari SharedPreferences di UI
                SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
                TextView tvStoreCoins = findViewById(R.id.tvStoreCoins);
                if (tvStoreCoins != null) {
                    tvStoreCoins.setText(String.valueOf(prefs.getInt("TOTAL_KOIN", 0)));
                }
            });
        });
    }

    // Logika ketika user membeli barang
    private void prosesBeliItem(StoreItem item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Anggap pembelian berhasil
            UserInventory newPurchase = new UserInventory(item.itemId, true, false);
            db.storeDao().buyItem(newPurchase);

            runOnUiThread(() -> {
                Toast.makeText(StoreActivity.this, "Berhasil membeli: " + item.itemName, Toast.LENGTH_SHORT).show();
                loadDataDariDatabase(); // Refresh tampilan toko
            });
        });
    }

    // Logika ketika user memasang badge/title ke profil
    private void prosesEquipItem(StoreItem item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // 1. Copot title/badge
            db.storeDao().unequipAllItemsOfType(item.itemType);
            // 2. Pasang title/badge yang baru dipilih
            db.storeDao().equipItem(item.itemId);

            runOnUiThread(() -> {
                Toast.makeText(StoreActivity.this, item.itemName + " berhasil di-equip!", Toast.LENGTH_SHORT).show();
                loadDataDariDatabase(); // Refresh tampilan toko
            });
        });
    }
}