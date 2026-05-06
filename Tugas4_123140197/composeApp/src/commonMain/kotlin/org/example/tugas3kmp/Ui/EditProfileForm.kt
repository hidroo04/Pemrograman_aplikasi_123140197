package org.example.tugas3kmp.Ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EditProfileForm(
    editName: String,
    editBio: String,
    editEmail: String,
    editPhone: String,
    editLocation: String,
    editStudy: String,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onStudyChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    isDarkMode: Boolean
) {
    val textColor = if (isDarkMode) Color.White else Color(0xFF1C1B1F)
    val hintColor = if (isDarkMode) Color(0xFFAAAAAA) else Color(0xFF9E9E9E)
    val cardColor = if (isDarkMode) Color.White.copy(alpha = 0.08f) else Color.White.copy(alpha = 0.5f)
    val borderColor = if (isDarkMode) Color.White.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.3f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Edit Profil",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6650A4)
            )

            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)


            OutlinedTextField(
                value = editName,
                onValueChange = onNameChange,
                label = { Text("Nama", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = editBio,
                onValueChange = onBioChange,
                label = { Text("Bio", color = hintColor) },
                minLines = 3,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = editEmail,
                onValueChange = onEmailChange,
                label = { Text("Email", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = editPhone,
                onValueChange = onPhoneChange,
                label = { Text("Phone", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = editLocation,
                onValueChange = onLocationChange,
                label = { Text("Location", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = editStudy,
                onValueChange = onStudyChange,
                label = { Text("Study", color = hintColor) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6650A4),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF6650A4)
                    )
                ) {
                    Text("Batal", fontWeight = FontWeight.Medium)
                }


                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6650A4)
                    )
                ) {
                    Text("Simpan", color = Color.White, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

