package com.juandgaines.todoapp.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juandgaines.todoapp.ui.theme.AppTheme

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Text(
            title, modifier = Modifier,
            maxLines = 1,
            softWrap = false,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SectionTitlePreview() {
    AppTheme {
        SectionTitle("Section Title")
    }
}