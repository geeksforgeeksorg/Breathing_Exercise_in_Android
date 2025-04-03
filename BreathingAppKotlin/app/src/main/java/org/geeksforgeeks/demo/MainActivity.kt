@file:Suppress("DEPRECATION")
package org.geeksforgeeks.demo

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Text indicating INHALE, HOLD, EXHALE
    private lateinit var statusText: TextView
    // Circle that expands/contracts with breathing
    private lateinit var innerCircleView: View

    // Animation variables
    private lateinit var animationInhaleText: Animation
    private lateinit var animationExhaleText: Animation
    private lateinit var animationInhaleInnerCircle: Animation
    private lateinit var animationExhaleInnerCircle: Animation

    // Handler for timing the hold phase
    private val handler = Handler()

    // Durations for inhale, exhale, and hold phases (in milliseconds)
    private val inhaleDuration = 6000
    private val exhaleDuration = 6000
    private val holdDuration = 6000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.textView)
        innerCircleView = findViewById(R.id.circle)

        // Load animations for breathing phases
        prepareAnimations()

        // Start the breathing cycle with inhale
        startInhale()
    }

    // Loads and sets up animations for inhale and exhale
    private fun prepareAnimations() {
        // Inhale animation: Text grows and stays large
        animationInhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_inhale).apply {
            duration = inhaleDuration.toLong()
            // Keeps text in final state after animation
            fillAfter = true
            // Calls listener when animation ends
            setAnimationListener(inhaleAnimationListener)
        }
        animationInhaleInnerCircle =
            AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_inhale).apply {
                duration = inhaleDuration.toLong()
                // Keeps circle expanded
                fillAfter = true
            }

        // Exhale animation: Text shrinks and returns to normal
        animationExhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_exhale).apply {
            duration = exhaleDuration.toLong()
            setAnimationListener(exhaleAnimationListener)
        }
        animationExhaleInnerCircle =
            AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_exhale).apply {
                duration = exhaleDuration.toLong()
                // Keeps circle contracted
                fillAfter = true
            }
    }

    // Starts the inhale phase
    private fun startInhale() {
        statusText.text = "INHALE"
        statusText.startAnimation(animationInhaleText)
        innerCircleView.startAnimation(animationInhaleInnerCircle)
    }

    // Listener for inhale animation
    private val inhaleAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            statusText.text = "HOLD"
            // Wait for the hold duration, then start the exhale phase
            handler.postDelayed({ startExhale() }, holdDuration.toLong())
        }

        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
    }

    // Starts the exhale phase
    private fun startExhale() {
        statusText.text = "EXHALE"
        statusText.startAnimation(animationExhaleText)
        innerCircleView.startAnimation(animationExhaleInnerCircle)
    }

    // Listener for exhale animation
    private val exhaleAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            statusText.text = "HOLD"
            // Wait for the hold duration, then start inhale again
            handler.postDelayed({ startInhale() }, holdDuration.toLong())
        }

        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
    }
}
