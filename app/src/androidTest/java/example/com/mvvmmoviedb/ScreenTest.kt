/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import example.com.mvvmmoviedb.movies.MainActivity
import example.com.mvvmmoviedb.movies.MoviesAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for the different screens.
 * This test perform integration test where clicking on list should open detail screen.
 */
@RunWith(AndroidJUnit4::class)
class ScreenTest {

    /**
     * Interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @JvmField
    var tasksActivityTestRule =
            ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun resetState() {
        ViewModelFactory.destroyInstance()
    }

    /**
     * When opening app, movie list should be shown.
     */
    @Test
    fun openAppShouldShowMovieList() {
        onView(withText("Movies")).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.recycler_view)).check(matches(ViewMatchers.isDisplayed()))
    }

    /**
     * Integration test where clicking on list should open detail screen.
     */
    @Test
    fun itemClickShouldOpenDetailsScreen() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.ViewHolder>(0, click()));

        onView(withId(R.id.detail_icon)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.detail_title)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.detail_star)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.detail_release)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.detail_overview)).check(matches(ViewMatchers.isDisplayed()))
    }
}