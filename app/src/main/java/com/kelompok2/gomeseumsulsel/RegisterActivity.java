package com.kelompok2.gomeseumsulsel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        EditText etUsername = findViewById(R.id.etUsernameReg);
        EditText etNama = findViewById(R.id.etNamaReg);
        EditText etPassword = findViewById(R.id.etPasswordReg);
        Button btnDaftar = findViewById(R.id.btnRegister);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // Fungsi saat tombol Daftar ditekan
        btnDaftar.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || nama.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Trik: Mengubah username menjadi format email agar diterima Firebase
            String emailFormat = username + "@gomuseum.com";

            // Proses mendaftarkan akun ke Firebase
            mAuth.createUserWithEmailAndPassword(emailFormat, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
                            finish(); // Kembali ke halaman Login
                        } else {
                            Toast.makeText(RegisterActivity.this, "Gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Tombol kembali ke Login
        tvGoToLogin.setOnClickListener(v -> finish());
    }
}