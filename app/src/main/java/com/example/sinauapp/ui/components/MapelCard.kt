package com.example.sinauapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sinauapp.R

@Composable
fun MapelCard(
    modifier: Modifier = Modifier,
    mapelName: String,
    gradientColor: List<Color>,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .size(150.dp)
            .clickable { onClick() }
            .background(
                brush = Brush.verticalGradient(gradientColor),
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column (
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                id = R.drawable.img_books2),
                contentDescription = mapelName,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = mapelName,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}