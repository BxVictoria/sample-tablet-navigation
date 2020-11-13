package ru.dialog.tabletfragmentnavigation.navigation

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import ru.dialog.tabletfragmentnavigation.ui.*
import ru.dialog.tabletfragmentnavigation.ui.host.HostDualFragment

object Views {

    @Parcelize
    object FragmentDualHostView : AppScreen() {
        override fun getFragment(): Fragment? = HostDualFragment.create()

        override fun getTabletPosition() = TabletPosition.FULL
    }

    @Parcelize
    object FragmentAView : AppScreen() {
        override fun getFragment(): Fragment? = FragmentA()

        override fun getTabletPosition(): TabletPosition = TabletPosition.LEFT
    }

    @Parcelize
    object FragmentBView : AppScreen() {
        override fun getFragment(): Fragment? = FragmentB()

        override fun getTabletPosition(): TabletPosition = TabletPosition.RIGHT
    }

    @Parcelize
    object FragmentCView : AppScreen() {
        override fun getFragment(): Fragment? = FragmentC()

        override fun getTabletPosition(): TabletPosition = TabletPosition.RIGHT
    }

    @Parcelize
    object FragmentDView : AppScreen() {
        override fun getFragment(): Fragment? = FragmentD()

        override fun getTabletPosition(): TabletPosition = TabletPosition.FULL
    }

    @Parcelize
    object FragmentDialogView : AppScreen() {
        override fun getFragment(): Fragment? = FragmentDialog()

        override fun getTabletPosition(): TabletPosition = TabletPosition.DIALOG
    }
}