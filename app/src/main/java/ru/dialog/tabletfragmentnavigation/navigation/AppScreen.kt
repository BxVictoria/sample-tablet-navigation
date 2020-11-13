package ru.dialog.tabletfragmentnavigation.navigation

import android.os.Parcelable
import im.dlg.core_ui.component.swipe_back.SwipeBackFragment
import kotlinx.android.parcel.Parcelize
import ru.dialog.tabletfragmentnavigation.ui.SlideFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

@Parcelize
open class AppScreen : SupportAppScreen(), Parcelable {
    open fun getSlideFragment(): SlideFragment? {
        return null
    }

    open fun getSwipeableFragment(): SwipeBackFragment? {
        return null
    }

    open fun getTabletPosition(): TabletPosition = TabletPosition.FULL

    open fun getTabletChain(): Array<AppScreen> = arrayOf(this)
}
