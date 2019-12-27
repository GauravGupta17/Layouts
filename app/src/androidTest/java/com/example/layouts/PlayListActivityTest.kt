package com.example.layouts

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class PlayListActivityTest{


    @get:Rule
    val activityRule = ActivityTestRule<PlayListActivity>(PlayListActivity::class.java)

    @Before
    fun click_fab_botton(){
        onView(withId(R.id.fabAddSong)).perform(click())
    }

    @Test
    fun add_song(){
        onView(withId(R.id.etSongName)).perform(typeText("SongName"))
        onView(withId(R.id.etUrl)).perform(typeText("asjkdsd"))
        onView(withId(R.id.btnAddSong)).perform(click())

    }

    @After
    fun delete_song(){
        intended(allOf(hasComponent(hasShortClassName(".PlayListActivity")),hasExtra(PlayListActivity.SONGNAME,"songName")))
    }



}