# FetchCodingExercise
Android Application written in Kotlin


## Overview
This application fetches data from api, performs operations(business logic), and displays it in the UI using Jetpack Compose. It follows MVVM principles and manual dependency injection.


## Architecture
````plaintext
com.tg.android.fetchce/
├── data/               # Data layer: handles data related operations
│   ├── model/          # Data models returned by the API
│   ├── remote/         # Network components
│   └── repository/     # Repository implementations
|
├── di/                 # DI configurations
|
├── domain/             # Domain layer: business logic
│   ├── model/          # Domain models used by the application
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business use cases
|
└── ui/                 # Presentation layer: UI components
|   ├── components/     # UI components
|   ├── screens/        # Screen compositions
|   └── theme/          # UI theming
|
└── viewmodels/         # ViewModels that connect domain and UI
````


## Dependencies

This project uses the following dependencies:

### AndroidX Lifecycle
- `androidx.lifecycle.viewmodel.compose`: ViewModel integration with Jetpack Compose.

### Networking
- `retrofit`: High-Level Type-safe HTTP client
- `retrofit.converter.gson`: Retrofit converter for JSON serialization/deserialization using Gson.
- `okhttp`: Low-Level HTTP client
- `okhttp.logging.interceptor`: A logging interceptor for OkHttp to inspect and debug network requests and responses.

### Coroutines
- `kotlinx.coroutines.android` - Provides coroutine support
- `kotlinx.coroutines.test` - Testing utilities

These dependencies are managed using `libs.versions.toml` in Gradle. You need to have them correctly set up in your project.  
  
_**Note: This project does not use Hilt or Dagger for dependency injection. Instead, dependencies are manually instantiated in MainActivity.kt and passed down as constructor parameters**_


## User Permissions

```
    <uses-permission android:name="android.permission.INTERNET" />
```
Required for any networking functionality. Allows app to access the internet.
```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
Required for checking network and connectivity status. Allows app to check the state of the network.
