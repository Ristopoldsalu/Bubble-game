package com.example.risto.mull;

/**
 * Created by Risto on 6/24/2015.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class MainButton extends Button {

    private int size = 100;
    private boolean buttonShape;
    private int idd;
    private int multiplier;
    private boolean clicked;



    public MainButton(boolean shape, Context context, AttributeSet attrs) {
        super(context, attrs);
        buttonShape = shape;
        if (buttonShape) {
            setBackgroundResource(R.drawable.circle_button);
        } else {
            setBackgroundResource(R.drawable.square_button);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(size, size);
    }

    public int getSize() {
        return size;
    }

    public boolean getShape() { return buttonShape; }


    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getIdd() { return idd; }

    public void setMultiplier(int multi) {
        this.multiplier = multi;
    }

    public int getMultiplier() { return multiplier; }

    public void setClicked(boolean click) {
        this.clicked = click;
    }
    public boolean getClicked() { return clicked; }
}
