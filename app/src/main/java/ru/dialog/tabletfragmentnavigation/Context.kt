package ru.dialog.tabletfragmentnavigation

import android.content.Context

fun Context.isTablet(): Boolean =
    resources.getBoolean(R.bool.tablet)
