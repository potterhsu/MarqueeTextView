package com.potterhsu.marqueetextview.v4;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.potterhsu.marqueetextview.BuildConfig;
import com.potterhsu.marqueetextview.MarqueeTextView;
import com.potterhsu.marqueetextview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PoterHsu on 3/28/17.
 */

public class MarqueeTextFragment extends Fragment {

    public static final String TAG = MarqueeTextFragment.class.getSimpleName();

    private FrameLayout layoutRoot;
    private MarqueeInfo marqueeInfo;
    private List<String> textParts;

    private int viewWidth;
    private int viewHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marquee_text, container, false);

        layoutRoot = (FrameLayout) view.findViewById(R.id.layoutRoot);

        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                viewWidth = right - left;
                viewHeight = bottom - top;
            }
        });

        view.post(new Runnable() {
            @Override
            public void run() {
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                float textSize = Math.min(viewHeight * MarqueeTextView.TEXT_SCALE, MarqueeTextView.TEXT_MAX_SIZE);
                paint.setTextSize(textSize);

                int bufferWidth = 100;
                textParts = splitByTextWidth(marqueeInfo.getText(), viewWidth + bufferWidth, paint);

                for (String textPart : textParts) {
                    Log.d(TAG, "textPart: " + textPart);
                }

                startMarquee();
            }
        });

        return view;
    }

    private List<String> splitByTextWidth(String text, int splitWidth, Paint paint) {
        List<String> strings = new ArrayList<>();

        int start = 0;
        int end = start;

        while (start < text.length()) {
            while(true) {
                end += 1;
                if (end == text.length())
                    break;

                Rect bounds = new Rect();
                paint.getTextBounds(text, start, end, bounds);
                if (bounds.width() > splitWidth)
                    break;
            }

            strings.add(text.substring(start, end));
            start = end;
        }

        return strings;
    }

    public void startMarquee() {
        startMarquee(0, null);
    }

    public void startMarquee(final int index, final Integer initFrom) {
        final MarqueeTextView marqueeTextView = new MarqueeTextView(getActivity());
        marqueeTextView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        marqueeTextView.setText(textParts.get(index));
        marqueeTextView.setColor(marqueeInfo.getColor());
        marqueeTextView.setVisibility(View.INVISIBLE);
        layoutRoot.addView(marqueeTextView);

        marqueeTextView.post(new Runnable() {
            @Override
            public void run() {
                final float from = index == 0 ? viewWidth : initFrom;
                final float to = -1f * marqueeTextView.getTextWidthFromMeasurement();

                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "start: from = " + from + ", to = " + to);
                }

                Animation animation1 = new TranslateAnimation(from, 0, 0, 0);
                animation1.setDuration((long) ((from - 0) / marqueeInfo.getSpeed()));
                animation1.setInterpolator(new LinearInterpolator());
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        /** first stage **/
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, textParts.get(index) + " arrived first stage");
                        }

                        Animation animation2 = new TranslateAnimation(0, to, 0, 0);
                        animation2.setDuration((long) ((0 - to) / marqueeInfo.getSpeed()));
                        animation2.setInterpolator(new LinearInterpolator());
                        animation2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                /** second stage **/
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, textParts.get(index) + " arrived second stage");
                                }
                                // clear self
                                marqueeTextView.clearAnimation();
                                layoutRoot.removeView(marqueeTextView);

                                int childCount = layoutRoot.getChildCount();
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "childCount: " + childCount);
                                }
                                if (childCount == 0)    // after all marquee had run over, restart marquee
                                    startMarquee();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        marqueeTextView.startAnimation(animation2); // animation from `0` to `to`

                        // prepare for following marquee
                        if (index + 1 < textParts.size()) {
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, textParts.get(index + 1) + " is preparing");
                            }
                            startMarquee(index + 1, (int) marqueeTextView.getTextWidthFromMeasurement());
                        } else {
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, "no rest marquee");
                            }
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                marqueeTextView.startAnimation(animation1); // animation from `from` to `0`
                marqueeTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void stopMarquee() {
        for (int i = 0; i < layoutRoot.getChildCount(); ++i) {
            View v = layoutRoot.getChildAt(i);
            v.getAnimation().setAnimationListener(null);
            v.clearAnimation();
        }
        layoutRoot.removeAllViews();
    }

    public void setMarqueeInfo(MarqueeInfo marqueeInfo) {
        this.marqueeInfo = marqueeInfo;
    }

    public static class MarqueeInfo {
        private String text;
        private int color;
        private float speed;

        public MarqueeInfo(String text, int color, float speed) {
            this.text = text;
            this.color = color;
            this.speed = speed;
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
    }
}
