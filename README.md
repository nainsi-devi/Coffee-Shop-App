# Coffee Shop App ☕

A modern, feature-rich Android application for browsing and ordering coffee, built with Jetpack Compose and Firebase.

## 📱 Features

- **User Authentication:** Secure Login and Sign-up using Firebase Authentication.
- **Dynamic Home Screen:** Browse popular coffee types, view current offers, and search for your favorite blends.
- **Detailed Coffee Info:** View descriptions, ratings, and prices for various coffee items.
- **Local Cart Management:** Add items to your cart, update quantities, and clear the cart after checkout (powered by Room Database).
- **Favorites:** Mark your favorite coffees for quick access, stored locally for persistence.
- **User Profile:** Manage your account, view sign-in status, and access settings.
- **Settings & Privacy:** Securely update your password and find help/support information.
- **Responsive Design:** Fully scrollable screens with a clean, dark-themed UI.

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** Compose Navigation
- **Database:** Room (for local cart and favorites)
- **Backend:** Firebase (Auth, Firestore, Storage)
- **Networking:** Retrofit with GSON Converter
- **Image Loading:** Coil
- **Dependency Management:** Gradle Version Catalog (libs.versions.toml)

## 🚀 Getting Started

### Prerequisites

- Android Studio Ladybug or newer.
- A Firebase project with `google-services.json` added to the `app/` folder.
- Internet connection for fetching coffee data and authentication.

### Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.

