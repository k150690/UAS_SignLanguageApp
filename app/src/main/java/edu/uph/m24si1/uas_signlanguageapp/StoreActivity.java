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

    private Button btnTabTitle, btnTabFrame;
    private String kategoriAktif = "TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        db = DatabaseInitializer.getDatabase(this);

        rvStoreItems = findViewById(R.id.rvStoreItems);
        rvStoreItems.setLayoutManager(new LinearLayoutManager(this));

        btnTabTitle = findViewById(R.id.btnTabTitle);
        btnTabFrame = findViewById(R.id.btnTabFrame);

        btnTabTitle.setOnClickListener(v -> {
            kategoriAktif = "TITLE";
            btnTabTitle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            btnTabTitle.setTextColor(getResources().getColor(R.color.white));
            btnTabFrame.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.cream)));
            btnTabFrame.setTextColor(getResources().getColor(R.color.blue));
            loadDataDariDatabase();
        });

        btnTabFrame.setOnClickListener(v -> {
            kategoriAktif = "FRAME";
            btnTabFrame.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            btnTabFrame.setTextColor(getResources().getColor(R.color.white));
            btnTabTitle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.cream)));
            btnTabTitle.setTextColor(getResources().getColor(R.color.blue));
            loadDataDariDatabase();
        });

        adapter = new StoreAdapter(storeItemList, userInventoryList, new StoreAdapter.OnItemClickListener() {
            @Override
            public void onActionClick(StoreItem item, UserInventory inventory) {
                if (inventory == null || !inventory.isOwned) {
                    prosesBeliItem(item);
                } else if (!inventory.isEquipped) {
                    prosesEquipItem(item);
                }
            }
        });
        rvStoreItems.setAdapter(adapter);

        loadDataDariDatabase();
    }

    private void loadDataDariDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
            String activeEmail = prefs.getString("ACTIVE_EMAIL", "default");

            List<StoreItem> items = db.storeDao().getAllStoreItems();
            List<UserInventory> inventory = db.storeDao().getUserInventory(activeEmail); // Memanggil inventory user spesifik

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

                TextView tvStoreCoins = findViewById(R.id.tvStoreCoins);
                if (tvStoreCoins != null) {
                    int koinAktual = prefs.getInt("TOTAL_KOIN_" + activeEmail, 0);
                    tvStoreCoins.setText(String.valueOf(koinAktual));
                }
            });
        });
    }

    private void prosesBeliItem(StoreItem item) {
        SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
        String activeEmail = prefs.getString("ACTIVE_EMAIL", "default");

        int koinSekarang = prefs.getInt("TOTAL_KOIN_" + activeEmail, 0);

        // PERBAIKAN: Tarik harga asli dari properti barang, bukan angka mati 50
        int hargaBarang = item.itemPrice;

        if (koinSekarang < hargaBarang) {
            Toast.makeText(this, "Koin tidak cukup! Butuh " + hargaBarang + " koin.", Toast.LENGTH_SHORT).show();
            return;
        }

        int sisaKoin = koinSekarang - hargaBarang;
        prefs.edit().putInt("TOTAL_KOIN_" + activeEmail, sisaKoin).apply();

        Executors.newSingleThreadExecutor().execute(() -> {
            UserInventory newPurchase = new UserInventory(item.itemId, activeEmail, true, false);
            db.storeDao().buyItem(newPurchase);

            runOnUiThread(() -> {
                Toast.makeText(StoreActivity.this, "Berhasil membeli: " + item.itemName, Toast.LENGTH_SHORT).show();
                loadDataDariDatabase();
            });
        });
    }

    private void prosesEquipItem(StoreItem item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SharedPreferences prefs = getSharedPreferences("SignTeachPrefs", MODE_PRIVATE);
            String activeEmail = prefs.getString("ACTIVE_EMAIL", "default");

            db.storeDao().unequipAllItemsOfType(item.itemType, activeEmail);
            db.storeDao().equipItem(item.itemId, activeEmail);

            SharedPreferences.Editor editor = prefs.edit();
            if (item.itemType.equalsIgnoreCase("TITLE")) {
                editor.putString("EQUIPPED_TITLE_" + activeEmail, item.itemName);
            } else if (item.itemType.equalsIgnoreCase("FRAME")) {
                editor.putString("EQUIPPED_FRAME_" + activeEmail, item.itemId);
            }
            editor.apply();

            runOnUiThread(() -> {
                Toast.makeText(StoreActivity.this, item.itemName + " berhasil di-equip!", Toast.LENGTH_SHORT).show();
                loadDataDariDatabase();
            });
        });
    }
}