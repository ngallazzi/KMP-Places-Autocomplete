# KMP Places Autocomplete 📌
[![Kotlin](https://img.shields.io/badge/kotlin-v2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.7.1-blue)](https://github.com/JetBrains/compose-multiplatform)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)

## Introduction
A simple Compose Multiplatform library to fill addresses, cities and countries in a form, based on Google
Places API by
Google https://developers.google.com/maps/documentation/places/web-service/autocomplete.
For Android and IOS

# Screenshots

<p align="center">
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/city.png" alt="City - Android" width="270" style="margin-right: 24px;"/>
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/country.png" alt="Country - Android" width="270" style="margin-right: 24px;"/>
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/address.png" alt="Address - Android" width="270"/>
</p>
<p align="center">
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/city.png" alt="City - IOS" width="270" style="margin-right: 24px;"/>
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/country.png" alt="Country - IOS" width="270" style="margin-right: 24px;"/>
  <img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/address.png" alt="Address - IOS" width="270"/>
</p>



## ⚙️ Setup
Please generate a valid Google Places API key and add it to your project. You can find the instructions to generate key on Google docs: https://developers.google.com/maps/documentation/places/web-service/get-api-key
Put your inside your project local.properties file:
```kotlin 
api_key=<YOUR_API_KEY>
```
Then add the dependency to your project:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.ngallazzi:KMP-Places-Autocomplete:<LAST_VERSION>")
}
```

## Usage
Just put **AddressAutocompleteForm** composable within your app theme. Handle state from related lambda

```kotlin
@Composable
fun YourLayout() {
    MaterialTheme { // Your custom theme material3, default
        AddressAutocompleteForm(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            onSubmit = { state ->
                // Handle state changes here
            })
    }
}
// Complete parameters list

@Composable
fun AddressAutocompleteForm(
    modifier: Modifier = Modifier,
    addressLabel: String = "Please enter your address",
    cityLabel: String = "City",
    countryLabel: String = "Country",
    postalCodeLabel: String = "Postal code",
    ctaLabel: String = "Submit",
    formVerticalSpacing: Dp = VERTICAL_SPACING,
    submitButtonHeight: Dp = BUTTON_HEIGHT,
    onSubmit: (state: AddressAutocompleteFormState) -> Unit,
)

data class AddressAutocompleteFormState(
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val postalCode: String = "",
)

```
## 💬 Feedback
Please feel free to open an issue if you have any feedback or suggestions. PRs are welcome too!

## ©️ Credits

KMP Places Autocomplete is brought to you by
these [contributors](https://github.com/ngallazzi/KMP-Places-Autocomplete/graphs/contributors).

## 📜 License

This project is licensed under the GNU GENERAL PUBLIC LICENSE - see
the [LICENSE.md](https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/LICENSE) file for details
