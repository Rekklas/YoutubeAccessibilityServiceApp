package com.kovedik.youtubeaccessibilityserviceapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

/**
 * Accessibility service that track click events from Youtube app
 *
 * I took two buttons from Youtube app: Search and Inbox and track them with AccessibilityService.
 * When user clicks one of these buttons service launches our application.
 */
class YoutubeAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.d(TAG, event.contentDescription.toString())

            // If user clicks inbox button or search button in Youtube app launch our application
            if (event.contentDescription.toString() == INBOX_BUTTON ||
                event.contentDescription.toString() == SEARCH_BUTTON) {
                launchApp()
            }
        }
    }

    private fun launchApp() {
        val launchIntent =
            packageManager.getLaunchIntentForPackage("com.kovedik.youtubeaccessibilityserviceapp")
        launchIntent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(launchIntent)
    }

    override fun onInterrupt() {

    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "OnServiceConnected")
        serviceInfo = AccessibilityServiceInfo().apply {
            flags = AccessibilityServiceInfo.DEFAULT
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            packageNames = arrayOf("com.google.android.youtube")
        }
    }

    companion object {
        private const val TAG = "AccessibilityService"
        private const val SEARCH_BUTTON = "Search"
        private const val INBOX_BUTTON = "Inbox"
    }
}