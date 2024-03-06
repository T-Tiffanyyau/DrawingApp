package com.example.drawingactivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test


class MainScreenTest {

    @Test
    fun onCreateView_setsOnClickListener() {
        launchFragmentInContainer<MainScreen>()
        onView(withId(R.id.go_drawing_screen)).check(matches(isDisplayed()))
    }

    @Test
    fun onCreateView_setsUpRecyclerView() {
        launchFragmentInContainer<MainScreen>()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun goDrawingScreenButton_isClickable() {
        launchFragmentInContainer<MainScreen>()
        onView(withId(R.id.go_drawing_screen)).check(matches(isClickable()))
    }

    @Test
    fun goDrawingScreenButton_performsClick() {
        launchFragmentInContainer<MainScreen>()
        onView(withId(R.id.go_drawing_screen)).perform(click())
    }

}