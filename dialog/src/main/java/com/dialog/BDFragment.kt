package com.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

open class BDFragment : DialogFragment() {
    //如果传入布局资源则表示通过 onCreateView 进行布局
    var layoutRes = 0
    var isFullScreen = false
    var isFocusable = true
    var softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutRes == 0) {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        if (dialog!!.window!!.isFloating) {
            return inflater.inflate(layoutRes, container, false)
        }
        val rootView = DFrameLayout(context!!)
        rootView.setOnTouchOutsideListener {
            if (isCancelable) {
                dismiss()
            }
        }
        rootView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        inflater.inflate(layoutRes, rootView, true)
        return rootView
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        //如果不通过 onCreateView 布局的话不用覆写此方法
        //通过 onCreateDialog 布局的话覆不覆写这里没有影响
        if (layoutRes == 0) {
            return super.onGetLayoutInflater(savedInstanceState)
        }
        if (theme == 0) {
            setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        }
        super.onGetLayoutInflater(savedInstanceState)
        configWindow(dialog!!.window!!)
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        return activity!!.layoutInflater
    }

    /*
    // 解决黑色状态栏的问题
    AppUtils.setStatusBarTranslucent(window, true);
    AppUtils.setStatusBarColor(window, Color.TRANSPARENT, false);
    */

    private fun configWindow(window: Window) {
        window.setSoftInputMode(softInputMode)

        if (!isFocusable) {
            //弹出dialog时,保持隐藏状态栏、底部栏
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        } else if (isFullScreen) {
            Utils.hideBottomUIMenu(window)
        }
    }
}