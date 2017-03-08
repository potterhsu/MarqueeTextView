# MarqueeTextView
Android library of custom view to implement marquee feature.

## Features
1. Fragment based, you can easily use marquee fragment in your layout.
2. Smooth, the text is slided by android animation.
3. Unlimited text size, since our approach will split text into multiple parts.

![MarqueeTextView Illustration](https://raw.githubusercontent.com/potterhsu/MarqueeTextView/master/github/MarqueeTextViewIllustration.gif)

## Setup
1.  In root build.gradle:
  ```
  allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
  }
  ````

2.  In target module build.gradle
  ```
  dependencies {
    compile 'com.github.potterhsu:MarqueeTextView:2.0'
  }
  ```

## Usage

1. Initialize and automatically start
  ```java
  private MarqueeTextFragment marqueeTextFragment;
  marqueeTextFragment = new MarqueeTextFragment();
  marqueeTextFragment.setMarqueeInfo(
          new MarqueeTextFragment.MarqueeInfo("Some Text", Color.BLUE, 0.5f)
  );
  ```

2. Stop
  ```java
  marqueeTextFragment.stopMarquee();
  ```

3. After stopping, you may want to start again
  ```java
  marqueeTextFragment.startMarquee();
  ```
