package com.kelompok2.gomeseumsulsel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etNameProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView tvEmailProfile = findViewById(R.id.tvEmailProfile);
        etNameProfile = findViewById(R.id.etNameProfile);
        Button btnSaveProfile = findViewById(R.id.btnSaveProfile);
        Button btnLogoutProfile = findViewById(R.id.btnLogoutProfile);

        if (user != null) {
            tvEmailProfile.setText(user.getEmail() != null ? user.getEmail().replace("@gomuseum.com", "") : "");
            etNameProfile.setText(user.getDisplayName());
        }

        btnSaveProfile.setOnClickListener(v -> saveProfile(user));

        btnLogoutProfile.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveProfile(FirebaseUser user) {
        if (user == null) return;
        
        String newName = etNameProfile.getText().toString().trim();
        if (newName.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_name_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, getString(R.string.profile_update_success), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, getString(R.string.profile_update_failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}