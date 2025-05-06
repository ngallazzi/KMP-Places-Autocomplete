package com.ngallazzi.places.presentation.addressautocomplete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngallazzi.places.presentation.PlaceAutoCompleteTextField

private val VERTICAL_SPACING = 16.dp

@Composable
fun AddressAutocompleteForm(
    modifier: Modifier = Modifier,
    onStateChanged: (AddressAutocompleteFormState) -> Unit
) {
    val viewModel: AddressAutocompleteFormViewModel = remember {
        AddressAutocompleteFormViewModel()
    }
    val state = viewModel.uiState.collectAsState().value
    onStateChanged(state)

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaceAutoCompleteTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Please enter your address",
            onSuggestionSelected = { placeDetails ->
                viewModel.onAddressSelected(placeDetails)
            },
            text = state.address,
            onClearText = {
                viewModel.onAddressCleared()
            }
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("City") },
            readOnly = true,
            value = state.city,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Country") },
            readOnly = true,
            value = state.country,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Postal code") },
            value = state.postalCode,
            onValueChange = {
                viewModel.onPostalCodeChanged(it)
            }
        )
    }
}