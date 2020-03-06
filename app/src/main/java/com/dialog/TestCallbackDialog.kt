package com.dialog

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.dialog_test.view.*

/**
 * 添加人：  Tom Hawk
 * 添加时间：2020/3/6 16:54
 * 功能描述：
 * <p>
 * 修改人：  Tom Hawk
 * 修改时间：2020/3/6 16:54
 * 修改内容：
 */
class TestCallbackDialog : BDFragment() {
    private var callback: (() -> Unit)? = null

    fun setCallback(callback: () -> Unit) {
        this.callback = callback
    }

    init {
        layoutRes = R.layout.dialog_test
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.tvTip.setOnClickListener { callback?.invoke() }
    }
}