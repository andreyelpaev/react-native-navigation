package com.reactnativenavigation.params.animations;

import java.util.ArrayList;
import java.util.List;

public class ScreenTransitionsParams {
    public ScreenTransitionsParams() {
        nextShow = new ArrayList<>();
        prevHide = new ArrayList<>();
        prevShow = new ArrayList<>();
        nextHide = new ArrayList<>();
    }

    public List<ScreenAnimationParams> nextShow;
    public List<ScreenAnimationParams> prevHide;
    public List<ScreenAnimationParams> prevShow;
    public List<ScreenAnimationParams> nextHide;
}
