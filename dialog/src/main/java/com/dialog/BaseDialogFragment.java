package com.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);

        super.onGetLayoutInflater(savedInstanceState);
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        if (!getDialog().getWindow().isFloating()) {
            setupDialog();
            layoutInflater = new DialogLayoutInflater(requireContext(), layoutInflater,
                    new DialogFrameLayout.OnTouchOutsideListener() {
                        @Override
                        public void onTouchOutside() {
                            if (isCancelable()) {
                                dismiss();
                            }
                        }
                    });
        }
        return layoutInflater;
    }

    protected void setupDialog() {
        Window window = getDialog().getWindow();
        // 解决黑色状态栏的问题
        /*AppUtils.setStatusBarTranslucent(window, true);
        AppUtils.setStatusBarColor(window, Color.TRANSPARENT, false);*/

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isCancelable()) {
                        dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}