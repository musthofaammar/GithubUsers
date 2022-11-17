package id.eureka.githubusers.users.presentation.users

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import id.eureka.githubusers.R
import id.eureka.githubusers.TestUtil
import id.eureka.githubusers.core.util.EspressoIdlingResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UsersActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

//    @get:Rule(order = 1)
//    var scenarioRule = ActivityScenarioRule(UsersActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun goToUsersActivity_Success() = runBlocking {
        val activityScenario = ActivityScenario.launch(UsersActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_response.json"))

        mockWebServer.enqueue(mockResponse)

        val nextMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_empty_response.json"))

        mockWebServer.enqueue(nextMockResponse)

        onView(withId(R.id.search_box))
            .check(matches(isDisplayed()))

        delay(1000)

        onView(withId(R.id.tv_empty))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

//        onView(withId(R.id.rv_users))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    ViewActions.click()
//                )
//            )

        delay(1000)

        activityScenario.close()
    }

    @Test
    fun searchUser_Success() = runBlocking {
        val activityScenario = ActivityScenario.launch(UsersActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_response.json"))

        mockWebServer.enqueue(mockResponse)

        val emptyMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_empty_response.json"))

        mockWebServer.enqueue(emptyMockResponse)

        onView(withId(R.id.search_box))
            .check(matches(isDisplayed()))

        delay(1000)

        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

        onView(withId(R.id.ed_search_user))
            .perform(ViewActions.typeText("ahmad"))

        closeSoftKeyboard()

        val searchMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_with_query_response.json"))

        mockWebServer.enqueue(searchMockResponse)

        mockWebServer.enqueue(searchMockResponse)
        mockWebServer.enqueue(searchMockResponse)
        mockWebServer.enqueue(searchMockResponse)
        mockWebServer.enqueue(emptyMockResponse)

        delay(1500)

        onView(withId(R.id.rv_users))
            .check(matches(ViewMatchers.hasChildCount(1)))

        onView(withId(R.id.tv_empty))
            .check(matches(not(isDisplayed())))

        activityScenario.close()
    }

    @Test
    fun goToUsersActivityWithEmptyResponseAndEmptyDatabase_Success() = runBlocking {

        val activityScenario = ActivityScenario.launch(UsersActivity::class.java)

        val nextMockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtil.convertJsonFromString("search_empty_response.json"))

        mockWebServer.enqueue(nextMockResponse)

        onView(withId(R.id.search_box))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_empty))
            .check(matches(isDisplayed()))

        activityScenario.close()
    }
}