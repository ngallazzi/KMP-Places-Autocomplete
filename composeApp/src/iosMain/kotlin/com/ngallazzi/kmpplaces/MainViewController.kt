package com.ngallazzi.kmpplaces

import androidx.compose.ui.window.ComposeUIViewController
import com.ngallazzi.places.KMPPlaces

fun MainViewController() = ComposeUIViewController {
    KMPPlaces.initialize("your_api_key_here")
    App()
}