# Profile Screen
## Komponen Utama

### 1. ProfileHeader
Komponen ini menampilkan bagian atas profil, termasuk inisial pengguna di dalam lingkaran, nama, dan bio singkat.
- **Glass Effect**: Lingkaran avatar memiliki latar belakang putih transparan (`alpha = 0.2f`) dengan border putih yang lebih jelas.
- **Teks**: Nama menggunakan font `ExtraBold` dengan warna putih agar kontras dengan latar belakang gradien.

### 2. InfoItem
Komponen baris tunggal untuk menampilkan detail informasi seperti Email, Nomor Telepon, dan Lokasi.
- **Layout**: Terdiri dari ikon (emoji) dan kolom teks (label dan nilai).
- **Styling**: Label menggunakan warna putih transparan untuk menciptakan hierarki visual, sementara nilai utama menggunakan warna putih solid.

### 3. ProfileCard
Kontainer utama yang membungkus kumpulan `InfoItem`.
- **Glassmorphism Core**: Menggunakan `Card` dengan `containerColor` putih sangat transparan (`alpha = 0.15f`).
- **Visual Cues**: Memiliki `BorderStroke` putih tipis dan `elevation` yang diatur ke `0.dp` untuk memperkuat estetika "kaca datar".
- **Padding & Shape**: Menggunakan sudut melengkung yang besar (`24.dp`) dan padding internal yang luas untuk kenyamanan visual.

### 4. ActionButton
Tombol kustom yang mengikuti tema Glassmorphism.
- **Primary**: Tombol dengan latar belakang putih transparan dan border.
- **Secondary (Outline)**: Tombol dengan border putih yang lebih tebal tanpa latar belakang solid.

---

## Implementasi Kode

### ProfileScreen (Main Layout)
Layar utama menggunakan `Box` atau `Column` dengan `Brush.verticalGradient` sebagai latar belakang untuk memberikan efek kedalaman pada elemen "kaca" di atasnya.

```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF6650A4), Color(0xFFFF4081))
            )
        )
) {
}
```

### Efek Glass pada Card
```kotlin
Card(
    shape = RoundedCornerShape(24.dp),
    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
    colors = CardDefaults.cardColors(
        containerColor = Color.White.copy(alpha = 0.15f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
) {
}
```

## Screenshot 

