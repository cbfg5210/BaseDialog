package com.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DialogLayoutInflater(
    context: Context?,
    private val layoutInflater: LayoutInflater,
    private val listener: () -> Unit
) : LayoutInflater(context) {
    override fun cloneInContext(context: Context): LayoutInflater {
        return layoutInflater.cloneInContext(context)
    }

    override fun inflate(
        resource: Int,
        root: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        val rootLayout = DialogFrameLayout(context)
        rootLayout.setOnTouchOutsideListener(listener)
        rootLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutInflater.inflate(resource, rootLayout, true)
        return rootLayout
    }
}