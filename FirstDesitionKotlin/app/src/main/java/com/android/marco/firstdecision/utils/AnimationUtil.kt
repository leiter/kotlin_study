package com.android.marco.firstdecision.utils

import android.animation.*
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RelativeLayout
import com.android.marco.firstdecision.R
import java.util.*

/**
 * Created by gen on 28.05.15.


 */

class AnimationUtil {
    companion object {

        var TAG = AnimationUtil::class.java.simpleName
        var isInAnimation = false
        var listIsSliding = false
        var isFlipping = false

        fun AnimateBubbleSize(bag: ArrayList<Button>,
                              result: Button) {

            isInAnimation = true
            if (CountHandler.count !== 0) {
                CountHandler.count=CountHandler.count + bag.size
            } else {
                CountHandler.count=bag.size
            }

            val animatorSet = AnimatorSet()
            val randomRange = bag.size

            for (i in bag.indices) {
                val v = bag[i]
                val scaleDownX = ObjectAnimator.ofPropertyValuesHolder(v,
                        PropertyValuesHolder.ofFloat("scaleX", 1.5f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.5f),
                        PropertyValuesHolder.ofFloat("alpha", 0.3f))
                scaleDownX.duration = 100
                scaleDownX.repeatMode = ValueAnimator.REVERSE
                scaleDownX.repeatCount = 10
                scaleDownX.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        Log.d(TAG, Integer.toString(CountHandler.count))

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        CountHandler()
                        v.visibility = View.GONE
                        v.clearAnimation()
                        (v.parent as ViewManager).removeView(v)

                        if (CountHandler.count === 0) {
                            Log.d(TAG, "found if clausse")
                            val mView = bag[getRandom(0, randomRange)]
                            val aLayout = result.parent as RelativeLayout
                            if (aLayout.findViewById(R.id.container_rel).visibility == View.VISIBLE) {
                                slideInOut(aLayout.context, aLayout.findViewById(R.id.container_rel), true)
                                aLayout.findViewById(R.id.myFAB_middle).visibility = View.GONE

                            }
                            result.text = mView.text.toString()
                            result.visibility = View.VISIBLE
                            getResultAnimation(result)
                            isInAnimation = false
                            //                           v.clearAnimation();
                            bag.clear()

                        }
                        Log.d(TAG, Integer.toString(CountHandler.count))
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        var m = 1
                        var n = 1
                        if (Math.random() > 0.5) {
                            m = -1
                        }
                        if (Math.random() > 0.5) {
                            n = -1
                        }
                        scaleDownX.duration = getRandom(150, 200).toLong()
                        v.animate().translationX(getRandom(m * 100, 20).toFloat()).withLayer()
                        v.animate().translationY(getRandom(n * 100, 70).toFloat()).withLayer()
                    }

                })
                animatorSet.play(scaleDownX)
            }
            animatorSet.start()
        }


        fun getResultAnimation(v: View) {
            val o = ObjectAnimator.ofPropertyValuesHolder(v,
                    PropertyValuesHolder.ofFloat("scaleX", 0f, 1.0f),
                    PropertyValuesHolder.ofFloat("scaleY", 0f, 1.0f),
                    PropertyValuesHolder.ofFloat("alpha", 0.0f, 0.7f))
            o.duration = 300
            o.start()
        }

        fun getRandom(start: Int, delta: Int): Int {
            return start + (Math.random() * (start + delta)).toInt()
        }

        fun FlipImageView(context: Context, img_bottom: View, img_top: View) {

            if (!isFlipping) {
                isFlipping = true
                val repeat_count = 12

                val listener = object : Animator.AnimatorListener {
                    private val counter = 0
                    private val mRandom = Random()

                    override fun onAnimationStart(animation: Animator) {
                        if (counter == 0) {
                            img_top.background = ResourcesCompat.getDrawable(context.resources,
                                    R.drawable.sta256, context.theme)
                            img_bottom.background = ResourcesCompat.getDrawable(context.resources,
                                    R.drawable.tra64, context.theme)
                        }
                        //                else {counter++;}
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        //                counter--;
                        if (counter < 1) {
                            if (mRandom.nextBoolean()) {
                                img_bottom.background = ResourcesCompat.getDrawable(context.resources, R.drawable.sta256, context.theme)
                            } else {
                                img_bottom.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tra64, context.theme)
                            }

                            isFlipping = false
                        }


                        //                else { animation.start(); }
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {


                    }
                }

                val rotate_1 = ObjectAnimator.ofPropertyValuesHolder(img_top,
                        PropertyValuesHolder.ofFloat("rotationX", 0f, -180f),
                        PropertyValuesHolder.ofFloat("alpha", 1f, 0f))
                rotate_1.duration = 200
                rotate_1.interpolator = AccelerateDecelerateInterpolator()
                rotate_1.repeatCount = repeat_count

                val rotate_2 = ObjectAnimator.ofPropertyValuesHolder(img_bottom,
                        PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
                        PropertyValuesHolder.ofFloat("rotationX", 180f, 0f),
                        PropertyValuesHolder.ofFloat("alpha", 0f, 1f))
                rotate_2.duration = 200
                rotate_2.interpolator = AccelerateDecelerateInterpolator()
                rotate_2.repeatCount = repeat_count

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(rotate_1, rotate_2)
                animatorSet.addListener(listener)
                animatorSet.start()
            }


        }

        fun slideInOut(context: Context, layout: View, isShowing: Boolean) {
            val newViewState: Int
            if (isShowing && !listIsSliding) {
                layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out))
                newViewState = View.GONE
                listIsSliding = true
                layout.postDelayed({ listIsSliding = false }, 900)

            } else {
                layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in))
                newViewState = View.VISIBLE
            }
            layout.postDelayed({
                layout.clearAnimation()
                layout.visibility = newViewState
            }, 900)

        }
    }

}