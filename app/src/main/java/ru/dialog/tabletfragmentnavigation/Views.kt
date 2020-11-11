package ru.dialog.tabletfragmentnavigation

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import ru.dialog.tabletfragmentnavigation.navigation.AppScreen
import ru.dialog.tabletfragmentnavigation.navigation.TabletPosition

object Views {

    @Parcelize
    object FragmentHostView: AppScreen() {
        override fun getFragment(): Fragment? = HostFragment.create(TabletPosition.HOST)
    }

    @Parcelize
    object FragmentAView: AppScreen() {
        override fun getFragment(): Fragment? {
            return FragmentA()
        }

        override fun getTabletPosition(): TabletPosition {
            return TabletPosition.LEFT
        }
    }

    @Parcelize
    object FragmentBView: AppScreen() {
        override fun getFragment(): Fragment? {
            return FragmentB()
        }

        override fun getTabletPosition(): TabletPosition {
            return TabletPosition.RIGHT
        }
    }

    @Parcelize
    object FragmentCView: AppScreen() {
        override fun getFragment(): Fragment? {
            return FragmentC()
        }

        override fun getTabletPosition(): TabletPosition {
            return TabletPosition.RIGHT
        }
    }

    @Parcelize
    object FragmentDView: AppScreen() {
        override fun getFragment(): Fragment? {
            return FragmentD()
        }

        override fun getTabletPosition(): TabletPosition {
            return TabletPosition.RIGHT
        }
    }

    @Parcelize
    object FragmentDialogView: AppScreen() {
        override fun getFragment(): Fragment? {
            return FragmentDialog()
        }

        override fun getTabletPosition(): TabletPosition {
            return TabletPosition.DIALOG
        }
    }
}