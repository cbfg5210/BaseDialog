package com.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BDFragment : DialogFragment() {
    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        if (theme == 0) {
            setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        }
        super.onGetLayoutInflater(savedInstanceState)
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        var layoutInflater = activity!!.layoutInflater
        if (!dialog!!.window!!.isFloating) {
            /*
            // 解决黑色状态栏的问题
            AppUtils.setStatusBarTranslucent(window, true);
            AppUtils.setStatusBarColor(window, Color.TRANSPARENT, false);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            */
            layoutInflater = DLayoutInflater(requireContext(), layoutInflater) {
                if (isCancelable) {
                    dismiss()
                }
            }
        }
        return layoutInflater
    }
}