package com.ngallazzi.places.presentation.addressautocomplete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ngallazzi.places.presentation.PlaceAutoCompleteTextField

private val VERTICAL_SPACING = 16.dp

@Composable
fun AddressAutocompleteForm(
    modifier: Modifier = Modifier,
    onSubmit: (AddressAutocompleteFormState) -> Unit,
    addressLabel: String = "Please enter your address",
    cityLabel: String = "Please enter your city",
    countryLabel: String = "Please enter your country",
    postalCodeLabel: String = "Please enter your postal code",
    ctaLabel: String = "Click me"
) {
    val viewModel: AddressAutocompleteFormViewModel = remember {
        AddressAutocompleteFormViewModel()
    }
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaceAutoCompleteTextField(
            modifier = Modifier.fillMaxWidth(),
            label = addressLabel,
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
            label = { Text(cityLabel) },
            readOnly = true,
            value = state.city,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(countryLabel) },
            readOnly = true,
            value = state.country,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = { Text(postalCodeLabel) },
            value = state.postalCode,
            onValueChange = {
                viewModel.onPostalCodeChanged(it)
            }
        )
        Spacer(modifier = Modifier.height(VERTICAL_SPACING))
        Button(
            modifier = Modifier.fillMaxWidth(), enabled = state.isValid(),
            onClick = {
                onSubmit(state)
            }, content = {
                Text(ctaLabel)
            })
    }
}