package com.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BDFragment : DialogFragment() {
    //是否通过 onCreateView 布局
    var willCreateView = true
    var isFullScreen = false
    var isFocusable = true
    var softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        //如果不通过 onCreateView 布局的话不用覆写此方法
        //通过 onCreateDialog 布局的话覆不覆写这里没有影响
        if (!willCreateView) {
            return super.onGetLayoutInflater(savedInstanceState)
        }
        if (theme == 0) {
            setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        }
        super.onGetLayoutInflater(savedInstanceState)
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        var layoutInflater = activity!!.layoutInflater
        val window = dialog!!.window!!
        configWindow(window)
        if (!window.isFloating) {
            layoutInflater = DLayoutInflater(requireContext(), layoutInflater) {
                if (isCancelable) {
                    dismiss()
                }
            }
        }
        return layoutInflater
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
            Utils.hideBottomUIMenu(window, true)
        }
    }
}