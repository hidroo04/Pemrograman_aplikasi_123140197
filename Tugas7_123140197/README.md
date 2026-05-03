# MyNotes - Kotlin Multiplatform


## Penerapan Database dalam Kode

Proyek ini menggunakan **SQLDelight** untuk manajemen database lintas platform. Berikut adalah penjelasan mengenai cara database digunakan, disimpan, dan diterapkan dalam kodenya:

### 1. Penyimpanan Data
*   **Lokasi**: Data disimpan secara lokal di dalam perangkat menggunakan mesin SQLite.
*   **Platform Spesifik**: Pada Android, database disimpan dalam folder data aplikasi standar. Pada iOS, database disimpan dalam direktori dokumen aplikasi. Pengaturan ini dikelola secara otomatis melalui `SqlDriver` yang spesifik untuk masing-masing platform.

### 2. Definisi Skema dan Kueri
Skema database didefinisikan dalam file `.sq` (`NotesDatabase.sq`). SQLDelight secara otomatis menghasilkan kode Kotlin yang aman secara tipe (*type-safe*) untuk menjalankan kueri SQL tersebut.

```sql
CREATE TABLE NoteEntity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isDone INTEGER NOT NULL DEFAULT 0,
    updatedAt INTEGER NOT NULL
);
```

### 3. Arsitektur Data (*Data Source*)
Implementasi database dipisahkan ke dalam kelas `NoteLocalDataSource`. Berikut adalah poin-poin penting penerapannya:
*   **Reaktivitas**: Menggunakan `asFlow().mapToList()`, sehingga UI akan mendapatkan pembaruan secara *real-time* (otomatis) segera setelah data di database berubah.
*   **Asinkron**: Operasi database dijalankan di *thread* latar belakang menggunakan `Dispatchers.IO` untuk memastikan UI tetap responsif.
*   **Stempel Waktu**: Setiap kali catatan ditambah atau diubah, kolom `updatedAt` diperbarui secara otomatis menggunakan `kotlinx-datetime`.

## Fitur Utama

- **Mode Lihat**: Telusuri semua catatan Anda dengan UI yang indah dan penuh warna.
- **Mode Edit (CRUD)**: Menambah, melihat detail, atau menghapus catatan dengan mudah. Tombol hapus akan muncul saat mode ini diaktifkan.
- **Pencarian**: Fitur pencarian cepat berdasarkan judul atau isi catatan.
- **Bintang/Favorit**: Tandai catatan penting menggunakan ikon bintang.
- **UI Modern**: Desain kartu berbentuk pil yang unik dengan gradasi warna yang dinamis.

## Cara Membangun dan Menjalankan

### Android
- Di Windows: `.\gradlew.bat :composeApp:assembleDebug`
- Di macOS/Linux: `./gradlew :composeApp:assembleDebug`

### iOS
- Buka direktori `iosApp` di Xcode dan jalankan proyek tersebut.
