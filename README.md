# KMP Places Autocomplete 📌
[![Kotlin](https://img.shields.io/badge/kotlin-v2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.6.11-blue)](https://github.com/JetBrains/compose-multiplatform)
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
Just put **PlaceAutoCompleteTextField** composable within your app theme. Supported places are: City, Country, Address

```kotlin
@Composable
@Preview
fun App() {
    MaterialTheme {
        // Basic usage
        PlaceAutoCompleteTextField(
            label = "Please enter your city",
            type = City::class,
            onSuggestionSelected = {
                // DO SOMETHING
            }
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
            type = Address::class,
            isExtendedModeActive = true,
            onSuggestionSelected = {
                // DO SOMETHING
            }
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
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    isExtendedModeActive: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    languageCode: String = Locale.current.language,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    onSuggestionSelected: (String) -> Unit = {},
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
