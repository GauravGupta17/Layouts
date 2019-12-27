package com.example.layouts

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.adapters.ParentContactAdapter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ContactListActivityTest{

    @get:Rule
    val activityRule = ActivityTestRule<ContactListActivity>(ContactListActivity::class.java)

    @Test
    fun go_to_last_Element(){
        onView(withId(R.id.rvParent)).perform(RecyclerViewActions.scrollToPosition<ParentContactAdapter.ViewHolder>(2))
        onView(withId(R.id.rvChild))
    }

}