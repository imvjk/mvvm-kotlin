/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import example.com.mvvmmoviedb.data.model.Movie
import example.com.mvvmmoviedb.moviedetail.MovieDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Screen test for Movie Detail Screen.
 */
@RunWith(AndroidJUnit4::class)
class MovieDetailScreenTest {

    /**
     * Interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @JvmField
    var mActivityRule = object : ActivityTestRule<MovieDetailActivity>(MovieDetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation()
                    .targetContext
            return Intent(targetContext, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.MOVIE, Movie(1, "title", "path", "date", 1.toDouble(), "overview"))
            }
        }
    }

    /**
     * Integration test where clicking on list should open detail screen.
     */
    @Test
    fun testViews() {
        onView(ViewMatchers.withId(R.id.detail_icon)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.detail_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.detail_star)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.detail_release)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.detail_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}