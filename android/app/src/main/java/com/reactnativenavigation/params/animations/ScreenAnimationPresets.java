package com.reactnativenavigation.params.animations;

import java.util.ArrayList;
import java.util.List;

public class ScreenAnimationPresets {
    public static List<ScreenAnimationParams> getShowPreset(String name) {
        ScreenAnimationParams alpha = new ScreenAnimationParams(
                ScreenAnimationParams.Type.ALPHA,
                0f,
                1f,
                200,
                0,
                ScreenAnimationParams.Easing.Decelerate
        );

        switch (String.valueOf(name)) {
            case "fade": {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();
                list.add(alpha);
                return list;
            }
            case "slide-horizontal": {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();

                ScreenAnimationParams slideHorizontal = new ScreenAnimationParams(
                        ScreenAnimationParams.Type.TRANSLATION_X,
                        0.2f,
                        0f,
                        280,
                        0,
                        ScreenAnimationParams.Easing.Decelerate
                );

                list.add(alpha);
                list.add(slideHorizontal);

                return list;
            }
            default: {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();

                ScreenAnimationParams slideHorizontal = new ScreenAnimationParams(
                        ScreenAnimationParams.Type.TRANSLATION_Y,
                        0.2f,
                        0f,
                        280,
                        0,
                        ScreenAnimationParams.Easing.Decelerate
                );

                list.add(alpha);
                list.add(slideHorizontal);

                return list;
            }
        }
    }

    public static List<ScreenAnimationParams> getHidePreset(String name) {
        ScreenAnimationParams alpha = new ScreenAnimationParams(
                ScreenAnimationParams.Type.ALPHA,
                1f,
                0f,
                150,
                100,
                ScreenAnimationParams.Easing.Linear
        );

        switch (String.valueOf(name)) {
            case "fade": {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();
                list.add(alpha);
                return list;
            }
            case "slide-horizontal": {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();

                ScreenAnimationParams slide = new ScreenAnimationParams(
                        ScreenAnimationParams.Type.TRANSLATION_X,
                        0f,
                        0.2f,
                        250,
                        0,
                        ScreenAnimationParams.Easing.Accelerate
                );

                list.add(alpha);
                list.add(slide);

                return list;
            }
            default: {
                ArrayList<ScreenAnimationParams> list = new ArrayList<>();

                ScreenAnimationParams slide = new ScreenAnimationParams(
                        ScreenAnimationParams.Type.TRANSLATION_Y,
                        0f,
                        0.2f,
                        250,
                        0,
                        ScreenAnimationParams.Easing.Accelerate
                );

                list.add(alpha);
                list.add(slide);

                return list;
            }
        }
    }
}
