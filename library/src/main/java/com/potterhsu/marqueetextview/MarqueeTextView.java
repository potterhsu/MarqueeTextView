package com.potterhsu.marqueetextview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by PoterHsu on 12/30/16.
 */

public class MarqueeTextView extends View {

    public static final String TAG = MarqueeTextView.class.getSimpleName();
    private static final float DEFAULT_SPEED = 0.5f;

    private String text = "";
    private float speed = DEFAULT_SPEED;

    private Paint paint;
    private Rect viewBounds = new Rect();
    private Rect textBounds = new Rect();


    public MarqueeTextView(Context context) {
        super(context);
        initialize();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewBounds.set(0, 0, getWidth(), getHeight());

        float textSize = Math.min(viewBounds.height() * 0.8f, 300);
        paint.setTextSize(textSize);
        paint.getTextBounds(this.text, 0, this.text.length(), textBounds);

//        Log.d(TAG, "onMeasure: " +
//                        "viewBounds.width() = " + viewBounds.width() + ", " +
//                        "viewBounds.height() = " + viewBounds.height() + ", " +
//                        "textBounds.width() = " + textBounds.width() + ", " +
//                        "textBounds.height() = " + textBounds.height());
    }

    public void start() {
        float from = viewBounds.width();
        float to = -1f * textBounds.width();

        Animation animation = new TranslateAnimation(from, to, 0, 0);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration( (long) ((from - to) * (1 / speed)) );

        this.startAnimation(animation);
    }

    public void stop() {
        this.clearAnimation();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        paint.getTextBounds(this.text, 0, this.text.length(), textBounds);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float y = (viewBounds.height() - paint.ascent() - paint.descent()) / 2;
        canvas.drawText(this.text, 0, y, paint);
    }
}
