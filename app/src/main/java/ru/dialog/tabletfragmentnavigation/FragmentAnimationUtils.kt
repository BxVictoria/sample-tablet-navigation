package ru.dialog.tabletfragmentnavigation

import androidx.fragment.app.FragmentTransaction


/**
 * Created by alexscrobot on 14.03.18.
 */
object FragmentAnimationUtils {

    fun defaultScreenAnimation(fragmentTransaction: FragmentTransaction?) {
        fragmentTransaction?.setCustomAnimations(0, 0)
    }
}