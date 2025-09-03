package com.asdf.bookpediamodulasi

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.asdf.bookpediamodulasi.navigation.NavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationHost()
    }
}