package com.paulklauser.cars

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TrimDetailTest {

    private val composeRule = createComposeRule()
    private val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val ruleChain: RuleChain = RuleChain.outerRule(hiltRule).around(composeRule)
        .around(ScreenshotFailureRule(ApplicationProvider.getApplicationContext()))

    @Test
    fun navigate_through_to_car_detail() {
        launchActivity<MainActivity>()

        composeRule.onNodeWithText("Ford").performClick()
        composeRule.onNodeWithText("F-150").performClick()
        composeRule.onNodeWithText("LE").performClick()
        composeRule.onNodeWithText("$20000").assertExists()
    }

}