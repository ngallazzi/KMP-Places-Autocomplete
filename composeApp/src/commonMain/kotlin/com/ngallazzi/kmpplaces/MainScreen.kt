package com.ngallazzi.kmpplaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngallazzi.places.presentation.addressautocomplete.AddressAutocompleteForm


@Composable
fun App() {
    Column {
        Material3CustomTheme { // Your custom theme material3, default
            AddressAutocompleteForm(modifier = Modifier.fillMaxWidth().padding(24.dp))
        }
    }
}