package com.potterhsu.marquee.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.potterhsu.marqueetextview.MarqueeTextView;

public class MainActivity extends Activity {

    private MarqueeTextView marqueeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        marqueeTextView = (MarqueeTextView) findViewById(R.id.tv);
        marqueeTextView.setText("This is a good day, 歡迎光臨！");
        marqueeTextView.setColor(Color.BLUE);
        marqueeTextView.setSpeed(0.5f);
    }

    public void OnBtnStartClick(View view) {
        marqueeTextView.start();
    }

    public void OnBtnStopClick(View view) {
        marqueeTextView.stop();
    }
}
