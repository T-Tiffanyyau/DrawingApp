# Drawing App

**Notice: The project is still under development.**

Welcome to the Drawing Activity App, a captivating and intuitive Android application for drawing aficionados. This app, developed in Android Studio using Kotlin, creates an inviting digital sketching environment for both seasoned artists and newcomers alike.

## Installation
1. **Clone or download the repository.**
2. **Open Android Studio** and choose 'Open an existing Android Studio project'.
3. **Navigate to and open** the downloaded project.
4. **Wait for Android Studio** to synchronize with Gradle files.
5. **Execute the app** on your device or emulator using the 'Run' button or the shortcut Shift + F10.

## Features
- **Custom Drawing Canvas**: A responsive canvas designed for drawing and sketching, supporting touch interactions.
- **Brush Customization**: Offers users the ability to tailor brush sizes and colors to their liking.
- **Canvas State Preservation**: Ensures that the canvas state is preserved and restored during device rotation or when activity changes occur.

## Advanced Features
- **Color Swatches**: Provides a selection of color swatches for enhanced creative expression.
- **Toolbar Interactions**: Intuitive toolbar buttons for easy navigation and tool customization.
- **Customizable Pen Size and Color**: Implements SeekBar for real-time adjustments of pen size.
- **Persisting Drawings**: Enables users to save their drawings for future access and editing.

## Programming Highlights
- **LiveData Observations**: The DrawingScreen fragment utilizes LiveData from the ViewModel for timely UI updates.
- **RecyclerView Integration**: For efficient listing and selection of drawings, the MainScreen uses a RecyclerView.
- **Dynamic Fragment Management**: DrawingViewModel manages fragment transitions responsively for a smooth user experience.
- **SQL in Drawing Repository**: The app uses SQL databases for efficient storage and retrieval of drawing data, ensuring robust data management.
- **Use of Composable**: Leveraging Jetpack Compose, the app utilizes composable functions for dynamic and efficient UI construction, enhancing the overall user experience.

## Last Updated
March 6, 2024

As the Drawing Activity App continues to evolve, we invite you to join us in this journey of creative exploration and innovation. Your feedback, suggestions, and contributions are invaluable in driving the development of this unique digital sketching platform.
