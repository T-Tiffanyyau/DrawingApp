# Drawing App

## About
The Drawing Activity App is an engaging and intuitive Android application designed for drawing enthusiasts. Built using Android Studio with Kotlin, it offers a user-friendly environment for creative expression through digital sketching.

### Introduction Video
Got to https://github.com/T-Tiffanyyau/DrawingApp/blob/main/DrawingAppDemo.mp4, press `View raw`.

## Installation
- Clone or download the repository.
- Open Android Studio and choose 'Open an existing Android Studio project'.
- Navigate to and open the downloaded project.
- Wait for Android Studio to synchronize with Gradle files.
- Execute the app on your device or emulator using the 'Run' button or the shortcut Shift + F10.

## Features
- **Custom Drawing Canvas**: A responsive canvas designed for drawing and sketching, supporting touch interactions.
- **Brush Customization**: Offers users the ability to tailor brush sizes and colors to their liking.
- **Canvas State Preservation**: Ensures that the canvas state is preserved and restored during device rotation or when activity changes occur.
- **Firestore Integration**: Uses Firebase Firestore to store and manage drawing data securely online, enabling easy sharing and persistent storage of user-generated content.

## Advanced Features
- **Color Swatches**: Provides a selection of color swatches for enhanced creative expression.
- **Toolbar Interactions**: Intuitive toolbar buttons for easy navigation and tool customization.
- **Customizable Pen Size and Color**: Implements SeekBar for real-time adjustments of pen size.
- **Persisting Drawings**: Enables users to save their drawings for future access and editing.
- **Data Sharing**: Allows users to share their creations with others via Firestore, promoting collaboration and community engagement.

## Programming Highlights
- **LiveData Observations**: The DrawingScreen fragment utilizes LiveData from the ViewModel for timely UI updates.
- **RecyclerView Integration**: For efficient listing and selection of drawings, the MainScreen uses a RecyclerView.
- **Dynamic Fragment Management**: DrawingViewModel manages fragment transitions responsively for a smooth user experience.
- **SQL in Drawing Repository**: Complements Firestore by using SQL databases for efficient data management.
- **Use of Composable**: Leveraging Jetpack Compose, the app utilizes composable functions for dynamic and efficient UI construction.

## Last Updated
May 4, 2024
