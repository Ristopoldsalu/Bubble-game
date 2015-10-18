package com.example.risto.mull;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CircleButton extends Button {

    private int size = 100;

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);



        setBackgroundResource(R.drawable.circle_button);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(size, size);
    }

    public int getSize() {
        return size;
    }

}