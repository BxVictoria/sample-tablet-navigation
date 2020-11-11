package ru.dialog.tabletfragmentnavigation.slidr;


import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import ru.dialog.tabletfragmentnavigation.slidr.model.SlidrConfig;
import ru.dialog.tabletfragmentnavigation.slidr.widget.SliderPanel;

class FragmentPanelSlideListener implements SliderPanel.OnPanelSlideListener {

    private final View view;
    private final SlidrConfig config;
    private FragmentManager customFragmentManager;


    FragmentPanelSlideListener(@NonNull View view, @NonNull SlidrConfig config) {
        this.view = view;
        this.config = config;
    }

    FragmentPanelSlideListener(@NonNull View view, @NonNull SlidrConfig config, @NonNull FragmentManager fragmentManager) {
        this.view = view;
        this.config = config;
        this.customFragmentManager = fragmentManager;
    }


    @Override
    public void onStateChanged(int state) {
        if (config.getListener() != null) {
            config.getListener().onSlideStateChanged(state);
        }
    }


    @Override
    public void onClosed() {
        if (config.getListener() != null) {
            if (config.getListener().onSlideClosed()) {
                return;
            }
        }

        if (customFragmentManager != null) {
            if (customFragmentManager.getBackStackEntryCount() == 0) {
                if (view.getContext() instanceof Activity) {
                    final Activity activity = (Activity) view.getContext();
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }
            } else {
                customFragmentManager.popBackStack();
            }
            return;
        }

        // Ensure that we are attached to a FragmentActivity
        if (view.getContext() instanceof FragmentActivity) {
            final FragmentActivity activity = (FragmentActivity) view.getContext();
            if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            } else {
                activity.getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void onOpened() {
        if (config.getListener() != null) {
            config.getListener().onSlideOpened();
        }
    }


    @Override
    public void onSlideChange(float percent) {
        if (config.getListener() != null) {
            config.getListener().onSlideChange(percent);
        }
    }
}
