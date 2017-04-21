package com.potterhsu.marqueetextview;

import android.graphics.Typeface;

/**
 * Created by PoterHsu on 4/21/17.
 */

public class MarqueeInfo {
    private String text;
    private int color;
    private float speed;
    private Typeface typeface;
    private int backgroundColor;
    private boolean isBlink;

    public MarqueeInfo(String text, int color, float speed, Typeface typeface, boolean isBlink) {
        this.text = text;
        this.color = color;
        this.speed = speed;
        this.typeface = typeface;
        this.isBlink = isBlink;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public boolean isBlink() {
        return isBlink;
    }

    public void setBlink(boolean blink) {
        isBlink = blink;
    }
}
