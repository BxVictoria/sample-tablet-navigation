package ru.dialog.tabletfragmentnavigation.util

import android.content.Context
import ru.dialog.tabletfragmentnavigation.R

fun Context.isTablet(): Boolean =
    resources.getBoolean(R.bool.tablet)
