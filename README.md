# KMP Places Autocomplete 📌

## Introduction
A simple Compose Multiplatform library to fill addresses, cities and countries in a form, based on Google
Places API by
Google https://developers.google.com/maps/documentation/places/web-service/autocomplete.
For Android and IOS

# Screenshots
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/city.png" alt="City - Android" width="300"/>
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/country.png" alt="Country - Android" width="300"/>
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/android/address.png" alt="Address - Android" width="300"/>
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/city.png" alt="City - IOS" width="300"/>
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/country.png" alt="Country - IOS" width="300"/>
<img src="https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/screenshots/ios/address.png" alt="Address - IOS" width="300"/>


## ⚙️ Setup
Please generate a valid Google Places API key and add it to your project. You can find the instructions to generate key on Google docs: https://developers.google.com/maps/documentation/places/web-service/get-api-key
Put your inside your project local.properties file:
```kotlin 
api_key=<YOUR_API_KEY>
```
## Usage
Just put **PlaceAutoCompleteTextField** composable within your app theme. Supported places are: City, Country, Address

```kotlin
@Composable
@Preview
fun App() {
    MaterialTheme {
        // Basic usage
        PlaceAutoCompleteTextField(
            label = "Please enter your city",
            type = City::class // or Country::class or Address::class
        )
    }
}
@Composable
@Preview
fun App() {
    MaterialTheme {
        // Extended mode usage 
        PlaceAutoCompleteTextField(
            label = "Please enter your city",
            type = City::class,
            isExtendedModeActive = true
        )
    }
}

// Complete parameters list
@Composable
fun PlaceAutoCompleteTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    text: String = "",
    type: KClass<out Place>,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isExtendedModeActive: Boolean = false,
    languageCode: String = Locale.current.language
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
