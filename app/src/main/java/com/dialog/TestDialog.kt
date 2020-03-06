package com.dialog

import android.os.Bundle
import android.view.View

/**
 * 添加人：  Tom Hawk
 * 添加时间：2020/3/5 16:53
 * 功能描述：
 * <p>
 * 修改人：  Tom Hawk
 * 修改时间：2020/3/5 16:53
 * 修改内容：
 */
class TestDialog : BDFragment() {
    /*
    E/dialog: onCreate
    E/dialog: onGetLayoutInflater
    E/dialog: onCreateDialog
    E/dialog: onCreateView
    E/dialog: onStart
    */

    init {
        layoutRes = R.layout.dialog_test
        isFullScreen = true
//        isFocusable = false
    }

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setTitle("Hello")
//            .setView(R.layout.dialog_test)
            .setMessage("Hello world!")
            .create()
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.tvTip.text = "Hello Hello"
    }
}