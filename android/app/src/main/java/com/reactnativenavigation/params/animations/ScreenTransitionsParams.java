package com.reactnativenavigation.params.animations;

import java.util.ArrayList;
import java.util.List;

public class ScreenTransitionsParams {
    public ScreenTransitionsParams() {
        nextScreenTransition = new ArrayList<>();
        previousScreenTransition = new ArrayList<>();
    }

    public List<ScreenAnimationParams> nextScreenTransition;
    public List<ScreenAnimationParams> previousScreenTransition;
}
