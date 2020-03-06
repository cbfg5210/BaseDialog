package com.dialog

import android.content.Context
import android.graphics.Rect
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.FrameLayout

internal class DFrameLayout(context: Context) : FrameLayout(context) {
    private var onTouchOutsideListener: (() -> Unit)? = null
    private val outRect = Rect()
    private var x = 0
    private var y = 0

    fun setOnTouchOutsideListener(onTouchOutsideListener: (() -> Unit)?) {
        this.onTouchOutsideListener = onTouchOutsideListener
    }

    private val gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent) = true

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            x = e.x.toInt()
            y = e.y.toInt()
            for (i in childCount - 1 downTo 0) {
                getChildAt(i).getHitRect(outRect)
                if (outRect.contains(x, y)) {
                    return false
                }
            }
            onTouchOutsideListener?.invoke()
            return true
        }
    })

    override fun onTouchEvent(event: MotionEvent) = gestureDetector.onTouchEvent(event)
}