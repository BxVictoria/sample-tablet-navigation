package ru.dialog.tabletfragmentnavigation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.Window

class StatusBarCommonAnimator {
    companion object {
        fun create(fromColor: Int, toColor: Int, window: Window): ValueAnimator {
            return ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor).apply {
                addUpdateListener {
                    val color = it.animatedValue as Int
                    window.statusBarColor = color
                }
            }
        }
    }
}