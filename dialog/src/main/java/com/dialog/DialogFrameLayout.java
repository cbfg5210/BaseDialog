package com.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class DialogFrameLayout extends FrameLayout {

    interface OnTouchOutsideListener {
        void onTouchOutside();
    }

    private GestureDetector gestureDetector;
    private OnTouchOutsideListener onTouchOutsideListener;
    private Rect outRect = new Rect();

    public void setOnTouchOutsideListener(OnTouchOutsideListener onTouchOutsideListener) {
        this.onTouchOutsideListener = onTouchOutsideListener;
    }

    public DialogFrameLayout(@NonNull Context context) {
        super(context);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                int count = getChildCount();
                for (int i = count - 1; i > -1; i--) {
                    getChildAt(i).getHitRect(outRect);
                    if (outRect.contains((int) e.getX(), (int) e.getY())) {
                        return false;
                    }
                }
                if (onTouchOutsideListener != null) {
                    onTouchOutsideListener.onTouchOutside();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}