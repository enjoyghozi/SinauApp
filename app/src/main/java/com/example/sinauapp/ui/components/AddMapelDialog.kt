package com.example.sinauapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sinauapp.domain.model.Mapel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMapelDialog(
    isOpen: Boolean,
    title: String = "Tambah/Ubah Mapel",
    selectedColors: List<Color>,
    mapelName: String,
    goalHours: String,
    onMapelNameChange: (String) -> Unit,
    onGoalHoursChange: (String) -> Unit,
    onColorChange: (List<Color>) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    var mapelNameError by rememberSaveable { mutableStateOf<String?> (null) }
    var goalHoursError by rememberSaveable { mutableStateOf<String?> (null) }

    mapelNameError = when {
        mapelName.isBlank() -> "Mapel harus diisi."
        mapelName.length < 3 -> "Mapel harus lebih dari 3 karakter."
        mapelName.length > 20 -> "Mapel harus kurang dari 20 karakter."
        else -> null
    }

    goalHoursError = when {
        goalHours.isBlank() -> "Waktu belajar harus diisi."
        goalHours.toFloatOrNull() == null -> "Waktu belajar harus berupa angka."
        goalHours.toFloat() <= 0 -> "Waktu belajar minimal 1 jam."
        goalHours.toFloat() > 24f -> "Waktu belajar harus kurang dari 24 jam."
        else -> null
    }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(
                text = title,
                fontFamily = MaterialTheme.typography.titleSmall.fontFamily
            ) },
            text = {
                Column {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Mapel.mapelCardColor.forEach { colors ->  
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(brush = Brush.verticalGradient(colors))
                                    .border(
                                        width = 1.dp,
                                        color = if (colors == selectedColors) Color.Black else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { onColorChange(colors) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = mapelName,
                        onValueChange = onMapelNameChange,
                        label = { Text(text = "Mata Pelajaran") },
                        singleLine = true,
                        isError = mapelNameError != null && mapelName.isNotBlank(),
                        supportingText = {
                            Text(text = mapelNameError.orEmpty())
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHours,
                        onValueChange = onGoalHoursChange,
                        label = { Text(text = "Waktu Belajar") },
                        singleLine = true,
                        isError = goalHoursError != null && goalHours.isNotBlank(),
                        supportingText = {
                            Text(text = goalHoursError.orEmpty())
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Batal")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = mapelNameError == null && goalHoursError == null
                ) {
                    Text(text = "Simpan")
                }
            }
        )
    }
}