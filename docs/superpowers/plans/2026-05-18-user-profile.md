# User Profile Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add an editable user profile feature and display the logged-in user's name on the main page.

**Architecture:** We use Firebase Auth `UserProfileChangeRequest` to save and update the user's `DisplayName`. The Home screen retrieves this name to greet the user.

**Tech Stack:** Android, Java, Firebase Auth

---

### Task 1: Update Registration to Save Name

**Files:**
- Modify: `app/src/main/java/com/kelompok2/gomeseumsulsel/RegisterActivity.java`

- [ ] **Step 1: Implement `UserProfileChangeRequest` after successful registration**

Modify the success callback of `createUserWithEmailAndPassword` to update the user's profile with the inputted name. Replace the success block.

```java
// Inside btnDaftar.setOnClickListener, replace the addOnCompleteListener block:
mAuth.createUserWithEmailAndPassword(emailFormat, password)
    .addOnCompleteListener(this, task -> {
        if (task.isSuccessful()) {
            // Add Name to Firebase Profile
            com.google.firebase.auth.FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                com.google.firebase.auth.UserProfileChangeRequest profileUpdates = new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(nama)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(profileTask -> {
                            Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
                            finish(); // Kembali ke halaman Login
                        });
            } else {
                Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
        }
    });
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/java/com/kelompok2/gomeseumsulsel/RegisterActivity.java
git commit -m "feat: save name to firebase profile on registration"
```

### Task 2: Update Home Screen Layout

**Files:**
- Modify: `app/src/main/res/layout/activity_home.xml`

- [ ] **Step 1: Add Greeting Header to `activity_home.xml`**

Add a horizontal `LinearLayout` inside the main vertical `LinearLayout` right before the `TextView` that says "Pilih Kategori Museum". Ensure it has an ID for the greeting text and the profile icon.

```xml
        <!-- Insert this right after the opening LinearLayout tag, before the "Pilih Kategori Museum" TextView -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:layout_marginTop="16dp"
            android:paddingHorizontal="8dp">

            <TextView
                android:id="@+id/tvGreetingHome"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Halo, Pengguna!"
                android:textColor="@color/black"
                android:textSize="18sp"
                android:textStyle="bold" />

            <ImageView
                android:id="@+id/ivProfileIcon"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:src="@android:drawable/ic_menu_myplaces"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:clickable="true"
                android:focusable="true"/>
        </LinearLayout>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/activity_home.xml
git commit -m "ui: add greeting and profile icon to home screen"
```

### Task 3: Bind Home Screen Header Logic

**Files:**
- Modify: `app/src/main/java/com/kelompok2/gomeseumsulsel/HomeActivity.java`

- [ ] **Step 1: Fetch and display the user's name**

In `HomeActivity.java`, retrieve `FirebaseAuth.getInstance().getCurrentUser()` and update `tvGreetingHome`. Also set an onClickListener on `ivProfileIcon` to open `ProfileActivity`.

```java
// Add to imports at the top:
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Inside onCreate:
        TextView tvGreetingHome = findViewById(R.id.tvGreetingHome);
        ImageView ivProfileIcon = findViewById(R.id.ivProfileIcon);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
            tvGreetingHome.setText("Halo, " + currentUser.getDisplayName() + "!");
        } else {
            tvGreetingHome.setText("Halo!");
        }

        ivProfileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/java/com/kelompok2/gomeseumsulsel/HomeActivity.java
git commit -m "feat: display user name and handle profile click in home screen"
```

### Task 4: Create Profile Layout

**Files:**
- Create: `app/src/main/res/layout/activity_profile.xml`

- [ ] **Step 1: Create the layout XML**

Create the file and add a simple vertical layout for displaying and editing the profile.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp"
    android:background="@android:color/white">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Profil Pengguna"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="@color/black"
        android:layout_gravity="center_horizontal"
        android:layout_marginBottom="32dp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Email / Username"
        android:textColor="@color/black" />

    <TextView
        android:id="@+id/tvEmailProfile"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="12dp"
        android:text="user@email.com"
        android:background="#EEEEEE"
        android:layout_marginBottom="16dp"
        android:textColor="@color/black"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Nama"
        android:textColor="@color/black"/>

    <EditText
        android:id="@+id/etNameProfile"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="12dp"
        android:background="@drawable/bg_search_rounded"
        android:layout_marginBottom="24dp"
        android:textColor="@color/black"/>

    <Button
        android:id="@+id/btnSaveProfile"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Simpan Perubahan"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnLogoutProfile"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Logout"
        android:backgroundTint="#D32F2F"/>

</LinearLayout>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/activity_profile.xml
git commit -m "ui: create profile layout"
```

### Task 5: Create ProfileActivity

**Files:**
- Create: `app/src/main/java/com/kelompok2/gomeseumsulsel/ProfileActivity.java`
- Modify: `app/src/main/AndroidManifest.xml`

- [ ] **Step 1: Implement `ProfileActivity.java` logic**

```java
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
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
```

- [ ] **Step 2: Register `ProfileActivity` in `AndroidManifest.xml`**

Inside the `<application>` block of `app/src/main/AndroidManifest.xml`, add the new activity.

```xml
        <!-- Insert below existing activities like HomeActivity -->
        <activity
            android:name=".ProfileActivity"
            android:exported="false" />
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/kelompok2/gomeseumsulsel/ProfileActivity.java app/src/main/AndroidManifest.xml
git commit -m "feat: create profile activity logic and registration"
```