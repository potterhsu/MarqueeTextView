package com.potterhsu.marqueetextview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by PoterHsu on 12/30/16.
 */

public class MarqueeTextView extends View {

    public static final String TAG = MarqueeTextView.class.getSimpleName();
    public static final float TEXT_SCALE = 0.7f;
    public static final float TEXT_MAX_SIZE = 300;

    private String text = "";

    private Paint paint;
    private Rect viewBounds = new Rect();
    private Rect textBounds = new Rect();
    private float textWidthFromMeasurement;


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
        setVisibility(INVISIBLE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewBounds.set(0, 0,
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));

        float textSize = Math.min(viewBounds.height() * TEXT_SCALE, TEXT_MAX_SIZE);
        paint.setTextSize(textSize);
        paint.getTextBounds(this.text, 0, this.text.length(), textBounds);

        textWidthFromMeasurement = paint.measureText(this.text);

        /**
         * Note:
         *      String starts with `，` will make width measured by `getTextBounds` inaccurate,
         *      but by `measureText` is accurate.
         */

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onMeasure: " +
                    "viewBounds.width() = " + viewBounds.width() + ", " +
                    "viewBounds.height() = " + viewBounds.height() + ", " +
                    "textBounds.width() = " + textBounds.width() + ", " +
                    "textBounds.height() = " + textBounds.height() + ", " +
                    "textWidthFromMeasurement = " + textWidthFromMeasurement);
        }
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

    public void setTypeface(Typeface typeface) {
        paint.setTypeface(typeface);
    }

    public Rect getViewBounds() {
        return viewBounds;
    }

    public Rect getTextBounds() {
        return textBounds;
    }

    public float getTextWidthFromMeasurement() {
        return textWidthFromMeasurement;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float y = (viewBounds.height() - paint.ascent() - paint.descent()) / 2;
        canvas.drawText(this.text, 0, y, paint);
    }
}
