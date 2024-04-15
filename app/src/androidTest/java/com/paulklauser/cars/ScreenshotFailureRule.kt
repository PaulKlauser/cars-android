package com.paulklauser.cars

import android.content.Context
import androidx.test.espresso.base.DefaultFailureHandler
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Delegate test failures to the Espresso handler that handles saving screenshots with test services.
 */
class ScreenshotFailureRule(private val context: Context) : TestWatcher() {
    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        DefaultFailureHandler(context).handle(e, null)
    }
}