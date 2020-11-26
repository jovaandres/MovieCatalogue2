package com.example.moviecatalogue

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.moviecatalogue.ui.search.SearchActivity
import com.example.moviecatalogue.core.utils.DummyData
import com.example.moviecatalogue.core.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SearchActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadSearchedMovie() {
        onView(withId(R.id.search)).perform(click())
        val query = "Up"
        val dummyMovie = DummyData.generateDummyDetailMovie()
        onView(withId(R.id.search_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.search_movie)).perform(SearchViewActionExtension.submitText(query))
        waitUntilViewIsDisplayed(withId(R.id.rv_movies))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(query)), click()
            )
        )
        waitUntilViewIsDisplayed(withId(R.id.name))
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.name)).check(matches(withText(dummyMovie.title)))

        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(withText(dummyMovie.release_date)))

        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(withText(dummyMovie.overview)))

        onView(withId(R.id.length)).check(matches(isDisplayed()))
        onView(withId(R.id.length)).check(matches(withText("${dummyMovie.runtime} min")))

        onView(withId(R.id.rating)).check(matches(isDisplayed()))

        onView(withId(R.id.background)).check(matches(isDisplayed()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))

        onView(isRoot()).perform(pressKey(KeyEvent.KEYCODE_BACK))
    }

    @Test
    fun loadSearchedTvShow() {
        onView(withId(R.id.search)).perform(click())
        val query = "Up"
        val title = "Keeping Up with the Kardashians"
        val dummyTvShow = DummyData.generateDummyDetailTvShow()
        onView(withId(R.id.view_pager)).perform(swipeLeft())

        onView(withId(R.id.search_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.search_tv_show)).perform(SearchViewActionExtension.submitText(query))
        waitUntilViewIsDisplayed(withId(R.id.rv_tv_shows))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(title)), click()
            )
        )
        waitUntilViewIsDisplayed(withId(R.id.name))
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.name)).check(matches(withText(dummyTvShow.title)))

        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(withText(dummyTvShow.first_air_date)))

        onView(withId(R.id.overview)).check(matches(isDisplayed()))
        onView(withId(R.id.overview)).check(matches(withText(dummyTvShow.overview)))

        onView(withId(R.id.episode)).check(matches(isDisplayed()))
        onView(withId(R.id.episode)).check(matches(withText("${dummyTvShow.number_of_episodes} Episodes")))

        onView(withId(R.id.length)).check(matches(isDisplayed()))
        onView(withId(R.id.length)).check(matches(withText("${dummyTvShow.number_of_seasons} Seasons")))

        onView(withId(R.id.rating)).check(matches(isDisplayed()))

        onView(withId(R.id.background)).check(matches(isDisplayed()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))

        onView(isRoot()).perform(pressKey(KeyEvent.KEYCODE_BACK))
    }

    class SearchViewActionExtension {

        companion object {

            fun submitText(text: String): ViewAction {
                return object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
                    }

                    override fun getDescription(): String {
                        return "Set text and submit"
                    }

                    override fun perform(uiController: UiController, view: View) {
                        (view as SearchView).setQuery(text, true)
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

}