package com.ngallazzi.kmpplaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.presentation.PlaceAutoCompleteTextField
import org.jetbrains.compose.ui.tooling.preview.Preview

private val VERTICAL_SPACING = 16.dp

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlaceAutoCompleteTextField(
                label = "Please enter your city",
                type = City::class
            )
            Spacer(modifier = Modifier.height(VERTICAL_SPACING))
            PlaceAutoCompleteTextField(label = "Please enter your country", type = Country::class)
            Spacer(modifier = Modifier.height(16.dp))
            PlaceAutoCompleteTextField(label = "Please enter your address", type = Address::class)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}