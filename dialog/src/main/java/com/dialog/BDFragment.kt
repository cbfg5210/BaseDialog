package com.dialog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment

open class BDFragment : DialogFragment() {
    var willCreateView = true

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
        if (!dialog!!.window!!.isFloating) {
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
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    */
}