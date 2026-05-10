package com.kelompok2.gomeseumsulsel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menghubungkan ID dari XML ke Java
        TextView btnSejarah = findViewById(R.id.btnMasukSejarah);
        TextView btnBudaya = findViewById(R.id.btnMasukBudaya);
        TextView btnKerajaan = findViewById(R.id.btnMasukKerajaan);
        TextView btnPerjuangan = findViewById(R.id.btnMasukPerjuangan);

        // Perintah klik untuk setiap kategori
        btnSejarah.setOnClickListener(v -> bukaDaftar("Sejarah"));
        btnBudaya.setOnClickListener(v -> bukaDaftar("Budaya"));
        btnKerajaan.setOnClickListener(v -> bukaDaftar("Kerajaan"));
        btnPerjuangan.setOnClickListener(v -> bukaDaftar("Perjuangan"));
    }

    // Metode bantuan agar kode tidak berulang-ulang
    private void bukaDaftar(String kategori) {
        Intent intent = new Intent(HomeActivity.this, ListMuseumActivity.class);
        intent.putExtra("KATEGORI_TERPILIH", kategori);
        startActivity(intent);
    }
}