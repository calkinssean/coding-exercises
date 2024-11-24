# Take-Home Coding Exercise

Welcome to the coding exercise! The goal of this project is to demonstrate your skills by building an app that displays a set of locations on a map in a performant manner. Please follow the instructions below to complete the exercise.

## Project Overview

This exercise involves fetching data from a JSON file stored in this repository and implementing a map view to display locations. You'll need to implement features like filtering locations by type and displaying additional information when a location is tapped.

## Instructions

1. **Fork the Repository:**
    - Please begin by forking this repository.
    - All of your changes should be committed to your fork.
    - Optional: Use pull requests with short descriptions to help explain as you go through

2. **Setup Project:**
    - Create an `android` or `ios` folder respectively in the root of this folder. This is where your project will live.

3. **Fetch Location Data:**
    - The repository contains a JSON file, `locations.json`, that lists various locations.
    - Use the raw file content feature of GitHub for fetching data (think of it as a mock API). Please point it to your forked repository, not the Voze repository.
        - `https://raw.githubusercontent.com/<GITHUB_ACCOUNT_NAME>/coding-exercises/master/mobile/map-locations/locations.json`
    - Your task is to parse this file and use the data within the app.

4. **Display Locations on a Map View:**
    - Create a map view in the app as the main view.
        - The data is centered around San Francisco. Please set the default location to there.
    - Plot the locations from the JSON file on the map using pins or markers.

5. **Filtering Locations:**
    - Implement a way to filter the displayed locations by `location_type`.
        - The assumption can be made the location_types are a static set and will not change.
        - You can provide a UI (e.g., dropdown menu, segmented control, etc) that allows users to select which types of locations to display on the map.
        - Please feel free to "steal" existing UI from other application such as Google Maps, Apple Maps, Zillow, etc for filtering or craft your own.
        - Optional: Distinguish between location types through the presentation of pin or markers.

6. **Additional Details on Tap:**
   - When a user taps on a location pin, show a view with more detailed information about that location.
        - Display all `attributes` inside this detail view.
        - Please feel free to "steal" existing UI from other application such as Google Maps, Apple Maps, Zillow, etc for details or craft your own.

## Requirements

- **Documentation:**
    - Please add any contextual implementation information into Implementation section at the bottom of this `README`.
    - Add code comments when relevant
    - Add information on how to get the application up and running into the Getting Started section at the bottom of this `README`.
- **Programming Language:**
    - iOS: Please focus on using Swift
    - Android: Please focus on using Kotlin (Java is still acceptable, however Kotlin is preferred)
- **Data Fetching:**
    - You may use the std library or a 3rd party library for making HTTP request.
        - If using a 3rd party library please explain why inside the Implementation section at the bottom of this `README`.
- **Map Integration:**
    - You may use any mapping library, such as MapKit
    - Please do not use a mapping library which requires an API key
- **UI/UX:**
    - Design a user-friendly interface to interact with the map and filter locations.
    - Keep it simple. This isn't a exercise to test your design skill.
- **Filtering:**
    - Implement efficient filtering of locations by their type.

## Submission

1. Once you have completed the exercise email a link to the forked repository.

## Evaluation Criteria

- **Correctness:** Does the app fetch and display data correctly?
- **UI/UX:** Is the map view intuitive and easy to use (within reason given lack of design criteria)?
- **Code Quality:** Is the code clean, readable, follow modern architecture per environment, and well-structured?
- **Filtering:** Is filtering by location type implemented efficiently?
- **Attention to Detail:** Does the app show detailed information when a pin is tapped?

Feel free to reach out if you have any questions. Good luck, and happy coding!

Do not edit any lines above this line break.

---

## Getting Started

To get this project up and running locally on your machine, follow these steps:

### Prerequisites

Make sure you have the following installed:

- **Android Studio** (latest stable version)

### Setting up the Project

1. Open the project in **Android Studio**.
2. Sync the project with Gradle files by clicking "Sync Now" when prompted.
3. If the project requires any missing dependencies, Android Studio will automatically prompt you to install them.

### Running the App

1. **Connect an Android Device or Start an Emulator**:
    - To run the app on a **physical device**, connect your Android device to your computer via USB.
    - Make sure your device is in **Developer Mode** and **USB debugging** is enabled. If you haven't done this yet, follow these instructions to enable Developer Mode:
        - [How to enable Developer Mode on Android](https://developer.android.com/studio/run/device)
        - After enabling Developer Mode, make sure **USB debugging** is turned on by going to **Settings** > **Developer options** > **USB debugging**.
    - Alternatively, you can run the app on an **Android Emulator**. If you don't have an emulator set up yet, you can create one using Android Studio's AVD (Android Virtual Device) Manager.

2. **Build and Deploy the App**:
    - In **Android Studio**, click the **"Run"** button (green play icon) located in the top toolbar.
    - Android Studio will build the app, and once the build is successful, it will automatically deploy the app to your connected device or emulator.

## Implementation

The app follows the **MVVM** (Model-View-ViewModel) architecture, where the **View** (UI) is separated from the **Model** (data handling) and the **ViewModel** (business logic). The app uses **Dagger Hilt** for dependency injection and **MutableStateFlow** for state management.

### Networking

The app uses **Retrofit** to fetch data from the remote `locations.json` file. Retrofit simplifies making HTTP requests and converting the JSON response into Kotlin data classes. Here's a brief overview of how Retrofit is used:

1. **Retrofit Service Interface**: Defines the API endpoints and HTTP methods.
2. **Data Classes**: Model the JSON response into Kotlin data classes (e.g., `Location`, `LocationType`).
3. **Repository**: Handles fetching data from the network and provides it to the `ViewModel`.

### Dependency Injection with Dagger Hilt

**Dagger Hilt** is used for managing dependencies and handling the lifecycle of objects within the app. With Hilt, we can easily inject dependencies into the `ViewModel`, network service, and repository classes. Here's a quick overview:

- **@HiltAndroidApp**: This annotation is used in the application class to set up Hilt.
- **@Inject**: This annotation is used to request dependencies that will be provided by Hilt.
- **@AndroidEntryPoint**: This annotation is used on Android components (like `Activity`, `Fragment`, etc.) to enable dependency injection.

By using Dagger Hilt, the app maintains cleaner, more maintainable code and simplifies the setup and management of dependencies.

### State Management with MutableStateFlow

The app uses **MutableStateFlow** (from Kotlin's **Flow** API) to manage state and handle UI updates reactively.

- **StateFlow** provides a way to emit and collect state in a more functional style.
- **MutableStateFlow** is used in the `ViewModel` to expose a mutable state object that the UI collects and reacts to.

This approach makes the app's state management more flexible and aligns with modern Kotlin practices for reactive programming.

### Map Integration

The app uses **OSMDroid** (OpenStreetMap Droid) for map integration. OSMDroid is a powerful and flexible mapping library for Android that doesn't require an API key, making it ideal for this project. The map is displayed as the main view, with location pins/markers representing each item from the JSON file.

### Filtering Locations

The app provides an intuitive UI for filtering locations by their type, using **selectable buttons** and a **search bar**. The filtering UI is inspired by Apple Maps and includes the following:

- **Selectable Buttons**: A row of buttons allows the user to quickly select a location type (e.g., parks, restaurants, landmarks). When a button is clicked, the app updates the map to show only locations matching the selected type.
- **Search Bar**: A search bar is available for users to search for locations by **location type** or **name**. The results are displayed below the search bar, making it easy to find and filter locations.
- **Bottom Sheet**: The search results and filtered locations are displayed in a bottom sheet, a sliding panel that allows users to interact with the content without leaving the map view. This provides an intuitive and space-efficient way to manage the UI.

### Location Details on Tap

When a user taps on a location pin, the app shows a detail view with more information about the selected location. The details view displays all attributes for the location, such as:

- **Name**
- **Type**
- **Estimated Revenue**
- **Description**

This feature is designed with inspiration from the **Google Maps** and **Apple Maps** apps, ensuring a familiar and user-friendly experience.

### Key Libraries Used

- **Retrofit**: For network requests.
- **OSMDroid**: For map integration (no API key required).
- **Jetpack Compose**: For building the UI in a declarative style.
- **Dagger Hilt**: For dependency injection.
- **MutableStateFlow**: For state management.
- **ViewModel**: For managing UI-related data in a lifecycle-conscious way.

### Why Retrofit?

We used **Retrofit** because it simplifies the process of making network requests and handling JSON responses. It automatically converts the raw JSON into Kotlin objects, allowing us to focus on business logic rather than manually parsing data.

### Why Dagger Hilt?

**Dagger Hilt** is used for its simplicity and power in managing dependencies. It helps keep the codebase clean by providing automatic and declarative dependency injection, reducing boilerplate code and making the app more testable.
