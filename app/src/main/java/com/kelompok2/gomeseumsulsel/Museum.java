package com.kelompok2.gomeseumsulsel;

public class Museum {
    private String nama;
    private String lokasi;
    private String deskripsi;
    private String gambar;
    private float rating; // Tambahan untuk fitur rating

    public Museum() {
        // Konstruktor kosong dibutuhkan oleh Firebase
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}