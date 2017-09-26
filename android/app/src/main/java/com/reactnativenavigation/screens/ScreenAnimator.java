package com.reactnativenavigation.screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.animations.ScreenAnimationParams;
import com.reactnativenavigation.params.animations.ScreenAnimationPresets;
import com.reactnativenavigation.params.animations.ScreenTransitionsParams;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.sharedElementTransition.SharedElementsAnimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

class ScreenAnimator {
    private static final int DURATION = 250;
    private static final int ALPHA_START_DELAY = 100;
    private static final int ALPHA_SHORT_DURATION = 150;
    private static final LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private final float translationY;
    private final float translationX;
    private Screen screen;

    ScreenAnimator(Screen screen) {
        this.screen = screen;
        translationY = ViewUtils.getWindowHeight(screen.activity);
        translationX = ViewUtils.getWindowWidth(screen.activity);
    }

    public void show(boolean animate, final Runnable onAnimationEnd, Screen previousScreen) {
        if (animate) {
            createShowAnimator(onAnimationEnd, previousScreen).start();
        } else {
            screen.setVisibility(View.VISIBLE);
            NavigationApplication.instance.runOnMainThread(onAnimationEnd, 200);
        }
    }

    public void show(boolean animate, final Runnable onAnimationEnd) {
        if (animate) {
            createShowAnimator(onAnimationEnd, null).start();
        } else {
            screen.setVisibility(View.VISIBLE);
            NavigationApplication.instance.runOnMainThread(onAnimationEnd, DURATION);
        }
    }

    public void show(boolean animate) {
        if (animate) {
            createShowAnimator(null, null).start();
        } else {
            screen.setVisibility(View.VISIBLE);
        }
    }

    public void hide(boolean animate, Runnable onAnimationEnd) {
        if (animate) {
            createHideAnimator(onAnimationEnd, null).start();
        } else {
            screen.setVisibility(View.INVISIBLE);
            onAnimationEnd.run();
        }
    }

    public void hide(boolean animate, Runnable onAnimationEnd, Screen previous) {
        if (animate) {
            createHideAnimator(onAnimationEnd, previous).start();
        } else {
            screen.setVisibility(View.INVISIBLE);
            onAnimationEnd.run();
        }
    }

    private Collection<Animator> getAnimators(Screen screen, List<ScreenAnimationParams> screenAnimationParamsList) {
        Collection<Animator> animators = new ArrayList<>();
        for (ScreenAnimationParams params : screenAnimationParamsList) {
            ObjectAnimator animator = getAnimator(screen, params);
            animators.add(animator);
        }
        return animators;
    }

    private ObjectAnimator getAnimator(Screen screen, ScreenAnimationParams params) {
        if (params == null) return null;
        Property<View, Float> type = params.type.getProperty();
        Float from = params.from;
        Float to = params.to;

        if (type == View.TRANSLATION_X) {
            from *= this.translationX;
            to *= this.translationX;
        } else if (type == View.TRANSLATION_Y) {
            from *= this.translationY;
            to *= this.translationY;
        }

        TimeInterpolator interpolator = params.easing.getInterpolator();
        Integer duration = params.duration;
        Integer delay = params.delay;

        ObjectAnimator animator = ObjectAnimator.ofFloat(screen, type, from, to);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.setStartDelay(delay);

        return animator;
    }

    private Animator createShowAnimator(final @Nullable Runnable onAnimationEnd, @Nullable Screen previousScreen) {
        AnimatorSet set = new AnimatorSet();
        Collection<Animator> animators = new ArrayList<>();
        ScreenTransitionsParams screenTransitionsParams = this.screen.screenParams.screenTransitionsParams;
        String animationType = this.screen.screenParams.animationType;

        if (screenTransitionsParams == null) {
            List<ScreenAnimationParams> nextShowAnimations = ScreenAnimationPresets.getShowPreset(animationType);
            animators.addAll(getAnimators(this.screen, nextShowAnimations));
        } else {
            List<ScreenAnimationParams> nextShowAnimations = screenTransitionsParams.nextScreenTransition;
            animators.addAll(getAnimators(this.screen, nextShowAnimations));

            List<ScreenAnimationParams> prevHideAnimations = screenTransitionsParams.previousScreenTransition;
            animators.addAll(getAnimators(previousScreen, prevHideAnimations));
        }

        set.playTogether(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                screen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onAnimationEnd != null) {
                    onAnimationEnd.run();
                }
            }
        });
        return set;
    }

    private Animator createHideAnimator(final Runnable onAnimationEnd, Screen previousScreen) {
        AnimatorSet set = new AnimatorSet();
        Collection<Animator> animators = new ArrayList<>();
        ScreenTransitionsParams screenTransitionsParams = this.screen.screenParams.screenTransitionsParams;
        String animationType = this.screen.screenParams.animationType;

        if (screenTransitionsParams == null) {
            List<ScreenAnimationParams> nextShowAnimations = ScreenAnimationPresets.getHidePreset(animationType);
            animators.addAll(getAnimators(this.screen, nextShowAnimations));
        } else {
        }

        set.playTogether(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimationEnd.run();
            }
        });
        return set;
    }

    void showWithSharedElementsTransitions(Runnable onAnimationEnd) {
        hideContentViewAndTopBar();
        screen.setVisibility(View.VISIBLE);
        new SharedElementsAnimator(this.screen.sharedElements).show(new Runnable() {
            @Override
            public void run() {
                animateContentViewAndTopBar(1, 280);
            }
        }, onAnimationEnd);
    }

    private void hideContentViewAndTopBar() {
        if (screen.screenParams.animateScreenTransitions) {
            screen.getContentView().setAlpha(0);
        }
        screen.getTopBar().setAlpha(0);
    }

    void hideWithSharedElementsTransition(Runnable onAnimationEnd) {
        new SharedElementsAnimator(screen.sharedElements).hide(new Runnable() {
            @Override
            public void run() {
                animateContentViewAndTopBar(0, 200);
            }
        }, onAnimationEnd);
    }

    private void animateContentViewAndTopBar(int alpha, int duration) {
        List<Animator> animators = new ArrayList<>();
        if (screen.screenParams.animateScreenTransitions) {
            animators.add(ObjectAnimator.ofFloat(screen.getContentView(), View.ALPHA, alpha));
        }
        animators.add(ObjectAnimator.ofFloat(screen.getTopBar(), View.ALPHA, alpha));
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setDuration(duration);
        set.start();
    }
}
