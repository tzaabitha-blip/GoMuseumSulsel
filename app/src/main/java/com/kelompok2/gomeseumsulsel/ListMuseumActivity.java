package com.kelompok2.gomeseumsulsel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ListMuseumActivity extends AppCompatActivity {

    private RecyclerView rvMuseum;
    private MuseumAdapter adapter;
    private List<Museum> listMuseum;
    private DatabaseReference dbRef;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_museum);

        String kategori = getIntent().getStringExtra("KATEGORI_TERPILIH");
        if (kategori == null || kategori.isEmpty()) {
            kategori = "Sejarah";
        }

        rvMuseum = findViewById(R.id.rvMuseum);
        etSearch = findViewById(R.id.etSearch); // Pastikan ID ini sesuai dengan XML kamu

        rvMuseum.setLayoutManager(new LinearLayoutManager(this));
        listMuseum = new ArrayList<>();

        // Inisialisasi adapter dengan list kosong terlebih dahulu
        adapter = new MuseumAdapter(listMuseum);
        rvMuseum.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance("https://kelompok2-project-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Museum")
                .child(kategori);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMuseum.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Museum m = data.getValue(Museum.class);
                    listMuseum.add(m);
                }
                // Gunakan updateList agar data filter juga ikut diperbarui
                adapter.updateList(listMuseum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListMuseumActivity.this, "Gagal ambil data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Logika untuk mengetik di Search Bar
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}