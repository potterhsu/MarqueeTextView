package com.potterhsu.marquee.demo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.potterhsu.marqueetextview.MarqueeInfo;
import com.potterhsu.marqueetextview.MarqueeTextFragment;

public class MainActivity extends Activity {

    private MarqueeTextFragment marqueeTextFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String text = "How are you today? 你今天好嗎？ 你今天好吗？ 今日は元気ですか？ 오늘 어떠니? Quid agis hodie? Comment vas-tu aujourd'hui? Πώς είσαι σήμερα? Kuinka voit tänään? วันนี้คุณเป็นอย่างไร?";
        marqueeTextFragment = new MarqueeTextFragment();
        marqueeTextFragment.setMarqueeInfo(
                new MarqueeInfo(text, Color.BLUE, 0.5f, Typeface.DEFAULT_BOLD, true)
        );
        getFragmentManager().beginTransaction()
                .replace(R.id.layoutMarquee, marqueeTextFragment)
                .commit();
    }

    public void OnBtnStartClick(View view) {
        marqueeTextFragment.startMarquee();
    }

    public void OnBtnStopClick(View view) {
        marqueeTextFragment.stopMarquee();
    }
}
