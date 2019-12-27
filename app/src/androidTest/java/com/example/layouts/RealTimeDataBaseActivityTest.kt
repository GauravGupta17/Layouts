package com.example.layouts



import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RealTimeDataBaseActivityTest{

    private lateinit var name:String
    private lateinit var area:String

    @get:Rule
    var activityTest = ActivityTestRule<RealTimeDataBaseActivity>(RealTimeDataBaseActivity::class.java)

    @Before
    fun stringInput(){
        name ="gaurav"
        area = "bangalore"
    }

    @Test
    fun typeTest(){
         onView(withId(R.id.etRealTimeName)).perform(typeText(name), closeSoftKeyboard())
         onView(withId(R.id.etRealTimeArea)).perform(typeText(area), closeSoftKeyboard())
         onView(withId(R.id.btnRTSubmit)).perform(click())
         onView(withId(R.id.tvUserDataRT)).check(matches(withText("$name $area")))

    }

}