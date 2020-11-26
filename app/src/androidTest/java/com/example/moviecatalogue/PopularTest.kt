package com.example.moviecatalogue

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.moviecatalogue.ui.popular.PopularActivity
import com.example.moviecatalogue.core.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PopularTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(PopularActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadPopularMovie() {
        Espresso.onView(ViewMatchers.withId(R.id.popular)).perform(ViewActions.click())
        waitUntilViewIsDisplayed(ViewMatchers.withId(R.id.rv_pop_movies))
        Espresso.onView(ViewMatchers.withId(R.id.rv_pop_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_pop_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.click()
            )
        )
        waitUntilViewIsDisplayed(ViewMatchers.withId(R.id.name))
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.length))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rating))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.background))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressKey(KeyEvent.KEYCODE_BACK))
    }

    @Test
    fun loadPopularTvShow() {
        Espresso.onView(ViewMatchers.withId(R.id.popular)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.pop_view_pager)).perform(ViewActions.swipeLeft())
        waitUntilViewIsDisplayed(ViewMatchers.withId(R.id.rv_pop_tv_shows))
        Espresso.onView(ViewMatchers.withId(R.id.rv_pop_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_pop_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.click()
            )
        )
        waitUntilViewIsDisplayed(ViewMatchers.withId(R.id.name))
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.episode))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.length))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rating))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.background))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressKey(KeyEvent.KEYCODE_BACK))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}