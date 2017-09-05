package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.animations.ScreenAnimationParams;
import com.reactnativenavigation.params.animations.ScreenTransitionsParams;

import java.util.ArrayList;
import java.util.List;

public class ScreenTransitionsParser {
    private static final String SCREEN_TRANSITIONS_NEXT_SHOW = "nextScreenTransition";
    private static final String SCREEN_TRANSITIONS_PREV_HIDE = "previousScreenTransition";

    public static ScreenTransitionsParams getScreenTransitions(Bundle bundle) {
        if (bundle == null) return null;

        ScreenTransitionsParams screenTransitionsParams = new ScreenTransitionsParams();

        screenTransitionsParams.nextScreenTransition =
                getScreenAnimations(bundle.getBundle(SCREEN_TRANSITIONS_NEXT_SHOW));

        screenTransitionsParams.previousScreenTransition =
                getScreenAnimations(bundle.getBundle(SCREEN_TRANSITIONS_PREV_HIDE));

        return screenTransitionsParams;
    }

    private static List<ScreenAnimationParams> getScreenAnimations(Bundle bundle) {
        if (bundle == null) {
            return new ArrayList<>();
        }

        List<ScreenAnimationParams> result = new ArrayList<>();
        for (String key : bundle.keySet()) {
            result.add(getScreenAnimation(bundle.getBundle(key)));
        }
        return result;
    }

    private static ScreenAnimationParams getScreenAnimation(Bundle bundle) {
        ScreenAnimationParams screenAnimationParams = new ScreenAnimationParams();
        screenAnimationParams.type = ScreenAnimationParams.Type.fromString(bundle.getString("type"));

        try {
            screenAnimationParams.from = Float.parseFloat(bundle.getString("from"));
        } catch (NullPointerException e) {
            screenAnimationParams.from = 0f;
        }

        try {
            screenAnimationParams.to = Float.parseFloat(bundle.getString("to"));
        } catch (NullPointerException e) {
            screenAnimationParams.to = 0f;
        }

        screenAnimationParams.duration = bundle.getInt("duration", 300);
        screenAnimationParams.delay = bundle.getInt("delay", 300);
        screenAnimationParams.easing = ScreenAnimationParams.Easing.fromString(bundle.getString("easing"));

        return screenAnimationParams;
    }
}
