package com.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_test, container, false)
    }
}