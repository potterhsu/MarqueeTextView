package com.potterhsu.marqueetextview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PoterHsu on 3/7/17.
 */

public class MarqueeTextFragment extends Fragment {

    private MarqueeTextView marqueeTextView;
    private MarqueeInfo marqueeInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marquee_text, container, false);
        marqueeTextView = (MarqueeTextView) view.findViewById(R.id.tvMarquee);
        marqueeTextView.setText(marqueeInfo.getText());
        marqueeTextView.setColor(marqueeInfo.getColor());
        marqueeTextView.setSpeed(marqueeInfo.getSpeed());
        marqueeTextView.start();
        return view;
    }

    public void startMarquee() {
        marqueeTextView.start();
    }

    public void stopMarquee() {
        marqueeTextView.stop();
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
