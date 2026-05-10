package com.kelompok2.gomeseumsulsel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailMuseumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_museum);

        // 1. Inisialisasi komponen dari XML
        ImageView imgDetailMuseum = findViewById(R.id.imgDetailMuseum);
        TextView tvDetailNama = findViewById(R.id.tvDetailNama);
        TextView tvDetailLokasi = findViewById(R.id.tvDetailLokasi);
        TextView tvDetailDeskripsi = findViewById(R.id.tvDetailDeskripsi);
        RatingBar ratingBar = findViewById(R.id.ratingMuseum); // Pastikan ID ini ada di XML
        Button btnMaps = findViewById(R.id.btnMaps);

        // 2. Menangkap data yang dikirim dari Adapter
        String nama = getIntent().getStringExtra("EXTRA_NAMA");
        String lokasi = getIntent().getStringExtra("EXTRA_LOKASI");
        String deskripsi = getIntent().getStringExtra("EXTRA_DESKRIPSI");
        String urlGambar = getIntent().getStringExtra("EXTRA_GAMBAR");

        // Menangkap rating (default 0 jika data tidak ditemukan)
        float rating = getIntent().getFloatExtra("EXTRA_RATING", 0f);

        // 3. Memasang data teks dan rating ke UI
        tvDetailNama.setText(nama);
        tvDetailLokasi.setText(lokasi);
        tvDetailDeskripsi.setText(deskripsi);
        ratingBar.setRating(rating);

        // 4. Memasang gambar menggunakan Glide
        Glide.with(this)
                .load(urlGambar)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .centerCrop()
                .into(imgDetailMuseum);

        // 5. Logika Tombol Google Maps
        btnMaps.setOnClickListener(v -> {
            if (lokasi != null && !lokasi.isEmpty()) {
                String kueriSpesifik = nama + " " + lokasi;

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(kueriSpesifik));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/search/" + Uri.encode(kueriSpesifik)));
                    startActivity(browserIntent);
                }
            }
        });
    }
}