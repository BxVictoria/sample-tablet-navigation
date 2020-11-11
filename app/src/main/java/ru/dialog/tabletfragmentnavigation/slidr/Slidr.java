package ru.dialog.tabletfragmentnavigation.slidr;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import ru.dialog.tabletfragmentnavigation.R;
import ru.dialog.tabletfragmentnavigation.slidr.model.SlidrConfig;
import ru.dialog.tabletfragmentnavigation.slidr.model.SlidrInterface;
import ru.dialog.tabletfragmentnavigation.slidr.widget.SliderPanel;


public final class Slidr {


    @NonNull
    public static SlidrInterface attach(@NonNull Activity activity, @IdRes int rootId) {
        return attach(activity, -1, -1, rootId);
    }



    @NonNull
    public static SlidrInterface attach(@NonNull Activity activity, @ColorInt int statusBarColor1,
                                        @ColorInt int statusBarColor2, @IdRes int rootId) {

        // Setup the slider panel and attach it to the decor
        final SliderPanel panel = attachSliderPanel(activity, null, rootId);

        // Set the panel slide listener for when it becomes closed or opened
        panel.setOnPanelSlideListener(new ColorPanelSlideListener(activity, statusBarColor1, statusBarColor2));

        // Return the lock interface
        return panel.getDefaultInterface();
    }


    @NonNull
    public static SlidrInterface attach(@NonNull Activity activity, @NonNull SlidrConfig config,
                                        @IdRes int rootId) {

        // Setup the slider panel and attach it to the decor
        final SliderPanel panel = attachSliderPanel(activity, config, rootId);

        // Set the panel slide listener for when it becomes closed or opened
        panel.setOnPanelSlideListener(new ConfigPanelSlideListener(activity, config));

        // Return the lock interface
        return panel.getDefaultInterface();
    }


    /**
     * Attach a new {@link SliderPanel} to the root of the activity's content
     */
    @NonNull
    private static SliderPanel attachSliderPanel(@NonNull Activity activity, @NonNull SlidrConfig config,
                                                 @IdRes int rootId) {
        // Hijack the decorview
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View oldScreen = decorView.getChildAt(0);
        decorView.removeViewAt(0);

        // Setup the slider panel and attach it to the decor
        SliderPanel panel = new SliderPanel(activity, oldScreen, config);
        panel.setId(rootId);
        oldScreen.setId(R.id.slidable_content);
        panel.addView(oldScreen);
        decorView.addView(panel, 0);
        return panel;
    }



    @NonNull
    public static SlidrInterface replace(
            @NonNull final View oldScreen,
            @NonNull final SlidrConfig config,
            @IdRes final int rootId,
            FragmentManager customFragmentManager
    ) {
        ViewGroup parent = (ViewGroup) oldScreen.getParent();
        ViewGroup.LayoutParams params = oldScreen.getLayoutParams();
        parent.removeView(oldScreen);

        // Setup the slider panel and attach it
        final SliderPanel panel = new SliderPanel(oldScreen.getContext(), oldScreen, config);
        panel.setId(rootId);
        oldScreen.setId(R.id.slidable_content);

        panel.addView(oldScreen);
        parent.addView(panel, 0, params);

        // Set the panel slide listener for when it becomes closed or opened
        if(customFragmentManager != null) {
            panel.setOnPanelSlideListener(new FragmentPanelSlideListener(oldScreen, config, customFragmentManager));
        } else  {
            panel.setOnPanelSlideListener(new FragmentPanelSlideListener(oldScreen, config));
        }

        // Return the lock interface
        return panel.getDefaultInterface();
    }

}
