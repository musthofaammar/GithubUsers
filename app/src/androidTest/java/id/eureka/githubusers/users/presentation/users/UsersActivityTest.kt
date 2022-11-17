package id.eureka.githubusers.users.presentation.users

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import id.eureka.githubusers.R
import id.eureka.githubusers.TestUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UsersActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var scenarioRule = ActivityScenarioRule(UsersActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun goToUsersActivity_Success() {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_response.json"))

        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.search_box))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

//        onView(withId(R.id.rv_users))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    ViewActions.click()
//                )
//            )
    }
}