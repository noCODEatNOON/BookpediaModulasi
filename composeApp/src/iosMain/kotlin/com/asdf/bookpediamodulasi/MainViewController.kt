package com.asdf.bookpediamodulasi

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.asdf.bookpediamodulasi.di.initKoin
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }