package org.geeksforgeeks.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Text indicating INHALE, HOLD, EXHALE
    private TextView statusText;
    // Circle that expands/contracts with breathing
    private View innerCircleView;

    // Animation variables
    private Animation animationInhaleText;
    private Animation animationExhaleText;
    private Animation animationInhaleInnerCircle;
    private Animation animationExhaleInnerCircle;

    // Handler for timing the hold phase
    private final Handler handler = new Handler();

    // Durations for inhale, exhale, and hold phases (in milliseconds)
    private final int inhaleDuration = 6000;
    private final int exhaleDuration = 6000;
    private final int holdDuration = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.textView);
        innerCircleView = findViewById(R.id.circle);

        // Load animations for breathing phases
        prepareAnimations();

        // Start the breathing cycle with inhale
        startInhale();
    }

    // Loads and sets up animations for inhale and exhale
    private void prepareAnimations() {
        // Inhale animation: Text grows and stays large
        animationInhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_inhale);
        animationInhaleText.setDuration(inhaleDuration);
        animationInhaleText.setFillAfter(true);
        animationInhaleText.setAnimationListener(inhaleAnimationListener);

        animationInhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_inhale);
        animationInhaleInnerCircle.setDuration(inhaleDuration);
        animationInhaleInnerCircle.setFillAfter(true);

        // Exhale animation: Text shrinks and returns to normal
        animationExhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_exhale);
        animationExhaleText.setDuration(exhaleDuration);
        animationExhaleText.setAnimationListener(exhaleAnimationListener);

        animationExhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_exhale);
        animationExhaleInnerCircle.setDuration(exhaleDuration);
        animationExhaleInnerCircle.setFillAfter(true);
    }

    // Starts the inhale phase
    private void startInhale() {
        statusText.setText("INHALE");
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);
    }

    // Listener for inhale animation
    private final Animation.AnimationListener inhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            statusText.setText("HOLD");
            // Wait for the hold duration, then start the exhale phase
            handler.postDelayed(MainActivity.this::startExhale, holdDuration);
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    // Starts the exhale phase
    private void startExhale() {
        statusText.setText("EXHALE");
        statusText.startAnimation(animationExhaleText);
        innerCircleView.startAnimation(animationExhaleInnerCircle);
    }

    // Listener for exhale animation
    private final Animation.AnimationListener exhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            statusText.setText("HOLD");
            // Wait for the hold duration, then start inhale again
            handler.postDelayed(MainActivity.this::startInhale, holdDuration);
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };
}
