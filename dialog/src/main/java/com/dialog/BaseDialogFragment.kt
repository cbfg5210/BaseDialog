package com.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment : DialogFragment() {
    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        super.onGetLayoutInflater(savedInstanceState)
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        var layoutInflater = activity!!.layoutInflater
        val mDialog = dialog!!
        val window = mDialog.window!!
        if (!window.isFloating) {
            setupDialog(mDialog, window)
            layoutInflater = DialogLayoutInflater(requireContext(), layoutInflater) {
                if (isCancelable) {
                    dismiss()
                }
            }
        }
        return layoutInflater
    }

    private fun setupDialog(mDialog: Dialog, window: Window) {
        // 解决黑色状态栏的问题
        /*AppUtils.setStatusBarTranslucent(window, true);
        AppUtils.setStatusBarColor(window, Color.TRANSPARENT, false);*/
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        mDialog.setOnKeyListener { _: DialogInterface?, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                if (isCancelable) {
                    dismiss()
                }
                return@setOnKeyListener true
            }
            false
        }
    }
}