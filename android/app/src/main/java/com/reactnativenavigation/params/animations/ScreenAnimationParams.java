package com.reactnativenavigation.params.animations;

import android.animation.TimeInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class ScreenAnimationParams {
    public enum Type {
        ALPHA("ALPHA", View.ALPHA),
        SCALE_X("SCALE_X", View.SCALE_X),
        SCALE_Y("SCALE_Y", View.SCALE_Y),
        TRANSLATION_X("TRANSLATION_X", View.TRANSLATION_X),
        TRANSLATION_Y("TRANSLATION_Y", View.TRANSLATION_Y);

        private String name;
        private Property<View, Float> property;

        Type(String name, Property<View, Float> property) {
            this.name = name;
            this.property = property;
        }

        public static Type fromString(String name) {
            for (Type type : values()) {
                if (type.name.equals(name)) {
                    return type;
                }
            }
            return TRANSLATION_X;
        }

        public Property<View, Float> getProperty() {
            return property;
        }
    }

    public enum Easing {
        AccelerateDecelerate("accelerateDecelerate", new AccelerateDecelerateInterpolator()),
        Accelerate("accelerate", new AccelerateInterpolator()),
        Decelerate("decelerate", new DecelerateInterpolator()),
        FastOutSlowIn("FastOutSlowIn", new FastOutSlowInInterpolator()),
        Linear("linear", new LinearInterpolator());

        private String name;
        private TimeInterpolator interpolator;

        Easing(String name, TimeInterpolator interpolator) {
            this.name = name;
            this.interpolator = interpolator;
        }

        public static Easing fromString(String name) {
            for (Easing easing : values()) {
                if (easing.name.equals(name)) {
                    return easing;
                }
            }
            return Linear;
        }

        public TimeInterpolator getInterpolator() {
            return interpolator;
        }
    }

    public ScreenAnimationParams() {

    }

    public ScreenAnimationParams(Type type, Float from, Float to, Integer duration, Integer delay, Easing easing) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.delay = delay;
        this.easing = easing;
    }

    public Type type;
    public Float from;
    public Float to;
    public Integer duration;
    public Integer delay;
    public Easing easing;
}
