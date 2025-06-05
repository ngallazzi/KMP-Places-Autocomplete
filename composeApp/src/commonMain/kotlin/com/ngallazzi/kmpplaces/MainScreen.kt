package com.ngallazzi.kmpplaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngallazzi.places.domain.PlaceDetails
import com.ngallazzi.places.presentation.PlaceAutoCompleteTextField
import com.ngallazzi.places.presentation.addressautocomplete.AddressAutocompleteForm


@Composable
fun App() {
    var placeDetailsState: PlaceDetails? by remember {
        mutableStateOf(null)
    }
    var error: Throwable? by remember {
        mutableStateOf(null)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Material3CustomTheme { // Your custom theme material3, default
            AddressAutocompleteForm(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                onSubmit = { state ->
                    // Handle state changes here
                })
            Spacer(modifier = Modifier.height(8.dp))
            PlaceAutoCompleteTextField(
                label = "Please search a place",
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                onSuggestionSelected = { placeDetails ->
                    placeDetailsState = placeDetails
                }, onError = {
                    error = it
                })
            if (error != null) {
                Text(modifier = Modifier.fillMaxWidth().padding(24.dp), text = error.toString())
            }
            placeDetailsState?.run {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "Place details: ${placeDetailsState.toString()}"
                )
            }
        }
    }
}