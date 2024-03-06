Still under development
# Drawing App
The Drawing Activity App is an engaging and intuitive Android application designed for drawing enthusiasts. Built using Android Studio with Kotlin, it offers a user-friendly environment for creative expression through digital sketching.

## Installation
1. Clone or download the repository.
2. Open Android Studio and select 'Open an existing Android Studio project'.
3. Find and open the downloaded project.
4. Wait for Android Studio to sync with gradle files.
5.Run the app on your device or emulator using the 'Run' button or Shift + F10.

## Features
- Custom Drawing Canvas: An interactive canvas that responds to touch for drawing and sketching.
- Brush Customization: Users can adjust brush sizes and colors.
- Canvas State Preservation: Saves and restores the canvas state during device rotation or activity changes.

## Advanced Features
Color Swatches: Users can select from a palette of color swatches.
Toolbar Interactions: Toolbar buttons for navigation and tool customization.
Customizable Pen Size and Color: SeekBar integration for dynamic pen size adjustments.
Persisting Drawings: Ability to save drawings for later access.

(Programming related)
LiveData Observations: The DrawingScreen fragment observes LiveData from the ViewModel for updates.
RecyclerView Integration: MainScreen uses a RecyclerView to list drawings, offering a seamless selection and transition experience.
Dynamic Fragment Management: Responsive fragment transitions managed by DrawingViewModel.

Last Updated
March 6, 2024
