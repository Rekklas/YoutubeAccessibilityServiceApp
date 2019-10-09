package com.kovedik.youtubeaccessibilityserviceapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo



/**
 * Accessibility service that track click events from Youtube app
 *
 * I took two buttons from Youtube app: Search and Inbox and track them with AccessibilityService.
 * When user clicks one of these buttons service launches our application.
 */
class YoutubeAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d(TAG, event.toString())
        if (event?.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val nodeInfo = event.source
            if (handleClick(nodeInfo)) {
                launchApp()
            }
        }
    }

    private fun launchApp() {
        val launchIntent =
            packageManager.getLaunchIntentForPackage("com.google.android.youtube")
        launchIntent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        launchIntent.putExtra(EXTRA_BUTTON_CLICKED, 1)
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
            packageNames = arrayOf("com.kovedik.youtubeaccessibilityserviceapp")
        }
    }

    private fun handleClick(nodeInfo: AccessibilityNodeInfo): Boolean {
        val list = nodeInfo.findAccessibilityNodeInfosByText("Open Youtube")
        if (list.size > 0) {
            for (node in list) {
                val parent = node.parent
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            return true
        } else
            return false
    }

    companion object {
        private const val TAG = "AccessibilityService"
        const val EXTRA_BUTTON_CLICKED = "extra_button_clicked"
    }
}