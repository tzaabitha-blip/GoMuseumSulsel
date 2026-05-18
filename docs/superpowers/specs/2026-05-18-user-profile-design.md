# Design Specification: User Profile & Home Greeting

## 1. Overview
The goal of this feature is to add an editable user profile to the GoMuseumSulsel Android app. Additionally, the logged-in user's name will be displayed prominently on the Home screen.

## 2. Architecture & Data Storage
- **Backend:** We will utilize Firebase Authentication's built-in user profile capabilities, specifically `UserProfileChangeRequest`.
- **Data Stored:** 
  - `DisplayName` (Name of the user)
  - `Email` (Already handled, but formed internally as `username@gomuseum.com`)
- **No External Database:** By leveraging Firebase Auth, we avoid the need to set up Firebase Realtime Database or Firestore, keeping the implementation lightweight and focused.

## 3. UI Components & Layouts

### 3.1. HomeActivity
- **Greeting Section:** A new horizontal `LinearLayout` will be added to the top of `activity_home.xml` (inside the main `LinearLayout`, before the "Pilih Kategori Museum" text).
- **Greeting Text:** A `TextView` displaying "Halo, [Nama User]!".
- **Profile Icon:** An `ImageView` or `ImageButton` aligned to the right (end) of the greeting section. Clicking this icon will navigate to the `ProfileActivity`.

### 3.2. ProfileActivity (New)
- **Layout (`activity_profile.xml`):**
  - **Title:** "Profil Pengguna"
  - **Name Field:** An `EditText` displaying the current name, allowing the user to edit it.
  - **Username/Email Field:** A `TextView` displaying the username (read-only).
  - **Save Button:** To submit the name change.
  - **Logout Button:** To sign out from Firebase Auth and return to `MainActivity` (Login screen).

### 3.3. RegisterActivity
- **Update Logic:** After a successful `createUserWithEmailAndPassword` call, the app must immediately execute a `UserProfileChangeRequest` to save the value from `etNamaReg` into the Firebase Auth user's `DisplayName`.

## 4. Flow
1. **Registration:** User enters Username, Name, and Password -> Account created -> Name saved to Firebase Auth -> Redirect to Login.
2. **Login:** User logs in -> Redirect to Home.
3. **Home:** App fetches `FirebaseAuth.getInstance().getCurrentUser().getDisplayName()` -> Updates greeting text -> User clicks profile icon -> Redirect to Profile.
4. **Profile Edit:** User changes name in `EditText` -> Clicks Save -> App executes `UserProfileChangeRequest` -> Updates UI with new name.
5. **Logout:** User clicks Logout -> `FirebaseAuth.getInstance().signOut()` -> Redirect to Login.

## 5. Security & Constraints
- Only authenticated users can access the Home and Profile screens.
- Name editing is restricted to the currently logged-in user's token via Firebase Auth SDK.