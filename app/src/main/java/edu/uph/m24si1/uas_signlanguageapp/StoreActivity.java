package edu.uph.m24si1.uas_signlanguageapp;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // 1. Inisialisasi Database Room
        db = DatabaseInitializer.getDatabase(this);

        // 2. Hubungkan RecyclerView dengan layout XML
        rvStoreItems = findViewById(R.id.rvStoreItems);
        rvStoreItems.setLayoutManager(new LinearLayoutManager(this));

        // 3. Pasang Adapter ke RecyclerView beserta Logika Klik Tombolnya
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

        // 4. Ambil data dari database untuk ditampilkan ke layar
        loadDataDariDatabase();
    }

    // Fungsi untuk mengambil data terbaru dari database
    private void loadDataDariDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<StoreItem> items = db.storeDao().getAllStoreItems();
            List<UserInventory> inventory = db.storeDao().getUserInventory();

            // Balikkan data ke UI utama agar layar refresh
            runOnUiThread(() -> {
                storeItemList.clear();
                storeItemList.addAll(items);
                userInventoryList.clear();
                userInventoryList.addAll(inventory);
                adapter.notifyDataSetChanged();
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