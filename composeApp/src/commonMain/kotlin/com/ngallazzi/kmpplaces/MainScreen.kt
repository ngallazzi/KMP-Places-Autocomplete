package com.ngallazzi.kmpplaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.presentation.PlaceAutoCompleteTextField
import org.jetbrains.compose.ui.tooling.preview.Preview


private val VERTICAL_SPACING = 16.dp

@Composable
@Preview
fun App() {
    val viewModel: MainScreenViewModel = viewModel { MainScreenViewModel() }
    val state by viewModel.uiState.collectAsState()
    Column {
        Material3CustomTheme { // Your custom theme material3, default
            Column(
                Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlaceAutoCompleteTextField(
                    label = "Please enter your address",
                    type = Address::class,
                    onSuggestionSelected = { placeDetails ->
                        viewModel.onAddressSelected(placeDetails)
                    },
                    text = state.address,
                    onClearText = {
                        viewModel.onAddressCleared()
                    }
                )
                Spacer(modifier = Modifier.height(VERTICAL_SPACING))
                PlaceAutoCompleteTextField(
                    label = "Please enter your city",
                    type = City::class,
                    onSuggestionSelected = { placeDetails ->
                        viewModel.onCitySelected(placeDetails)
                    },
                    enabled = false,
                    text = state.city
                )
                Spacer(modifier = Modifier.height(VERTICAL_SPACING))
                PlaceAutoCompleteTextField(
                    label = "Please enter your country",
                    type = Country::class,
                    onSuggestionSelected = { placeDetails ->
                        viewModel.onCountrySelected(placeDetails)
                    },
                    enabled = false,
                    text = state.country
                )
                Spacer(modifier = Modifier.height(VERTICAL_SPACING))
            }
        }
    }
}