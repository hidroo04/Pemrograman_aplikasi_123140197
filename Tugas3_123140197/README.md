# Profile Screen

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

<img width="257" height="574" alt="image" src="https://github.com/user-attachments/assets/259ed4df-177c-4085-8fa1-90bcc1302c34" />
