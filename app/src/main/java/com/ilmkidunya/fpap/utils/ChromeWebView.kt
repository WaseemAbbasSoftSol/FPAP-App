package com.ilmkidunya.fpap.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout

class ChromeWebView(val context:Context, val activity:Activity)  : WebChromeClient() {
    var mCustomView: View? = null
    private var mCustomViewCallback: CustomViewCallback? = null
    protected var mFullscreenContainer: FrameLayout? = null
    private var mOriginalOrientation = 0
    private var mOriginalSystemUiVisibility = 0
    override fun getDefaultVideoPoster(): Bitmap? {
        return if (mCustomView == null) {
            null
        } else BitmapFactory.decodeResource(context.getResources(), 2130837573)
    }

    override fun onHideCustomView() {
        (activity.getWindow().getDecorView() as FrameLayout).removeView(mCustomView)
        mCustomView = null
        activity.getWindow().getDecorView().setSystemUiVisibility(
            mOriginalSystemUiVisibility
        )
        activity.setRequestedOrientation(mOriginalOrientation)
        mCustomViewCallback!!.onCustomViewHidden()
        mCustomViewCallback = null
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onShowCustomView(
        paramView: View,
        paramCustomViewCallback: CustomViewCallback
    ) {
        if (mCustomView != null) {
            onHideCustomView()
            return
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        mCustomView = paramView
        mOriginalSystemUiVisibility =
            activity.getWindow().getDecorView().getSystemUiVisibility()
        mOriginalOrientation = activity.getRequestedOrientation()
        mCustomViewCallback = paramCustomViewCallback
        (activity.getWindow().getDecorView() as FrameLayout).addView(
            mCustomView,
            FrameLayout.LayoutParams(-1, -1)
        )
        activity.getWindow().getDecorView().setSystemUiVisibility(3846)
    }
}