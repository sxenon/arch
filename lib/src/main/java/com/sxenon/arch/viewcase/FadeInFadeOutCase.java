package com.sxenon.arch.viewcase;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * 淡入淡出动画
 * 所有的重复代码都是犯罪
 */
public class FadeInFadeOutCase implements IViewCase {
    private View view;
    private float inAlpha = 1.0f;
    private float outAlpha = 0.0f;
    private long duration = 1000;
    private int translationX = 0;
    private int translationY = 0;

    public FadeInFadeOutCase(View view) {
        this.view = view;
    }

    public void setTranslationY(int translationY) {
        this.translationY = translationY;
    }

    public void fadeIn(){
        if ( view.getVisibility() == View.VISIBLE ) {
            return;
        }

        PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX", translationX);
        PropertyValuesHolder translationY1 = PropertyValuesHolder.ofFloat("translationY", translationY);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", outAlpha, inAlpha);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, translationX1, translationY1);
        view.setVisibility(View.VISIBLE);
        objectAnimator.setDuration(duration).start();
    }

    public void fadeOut(){
        PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX", translationX);
        PropertyValuesHolder translationY1 = PropertyValuesHolder.ofFloat("translationY", -translationY);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", inAlpha, outAlpha);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, translationX1, translationY1);
        objectAnimator.setDuration(duration).start();

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
