package ru.dialog.tabletfragmentnavigation.navigation

import android.content.Context
import ru.dialog.tabletfragmentnavigation.util.isTablet
import ru.terrakok.cicerone.Screen

fun provideRouter(context: Context): DialogRouter =
    if (context.isTablet()) TabletRouterWrapper else SingleCiceroneHolder.INSTANCE.getRouter()

private object TabletRouterWrapper : DialogRouter(TabletPosition.FULL) {

    override fun newRootChain(vararg screens: Screen) {
        val screensByPosition = screens.asList()
            .groupBy { tabletPosition(it) }
        for (position in screensByPosition.keys) {
            openDualHostIfNeccessary(position)
            MultiCiceroneHolder.getRouter(position)
                .newRootChain(*screensByPosition.getValue(position).toTypedArray())
        }
        if (!screensByPosition.keys.contains(TabletPosition.DIALOG)) {
            onNewScreen()
        }
    }

    private fun openDualHostIfNeccessary(position: TabletPosition) {
        if (position == TabletPosition.LEFT || position == TabletPosition.RIGHT) {
            MultiCiceroneHolder.getRouter(TabletPosition.FULL)
                .navigateToSingleTop(Views.FragmentDualHostView)
        }
    }

    override fun newChain(vararg screens: Screen) {
        val screensByPosition = screens.asList()
            .groupBy { tabletPosition(it) }
        for (position in screensByPosition.keys) {
            openDualHostIfNeccessary(position)
            MultiCiceroneHolder.getRouter(position)
                .newChain(*screensByPosition.getValue(position).toTypedArray())
        }
        if (!screensByPosition.keys.contains(TabletPosition.DIALOG)) {
            onNewScreen()
        }
    }

    override fun newRootScreen(screen: Screen) {
        val tabletPosition = tabletPosition(screen)
        openDualHostIfNeccessary(tabletPosition)
        MultiCiceroneHolder.getRouter(tabletPosition).newRootScreen(screen)
        if (tabletPosition != TabletPosition.DIALOG) {
            onNewScreen()
        }
    }

    override fun replaceScreen(screen: Screen) {
        val tabletPosition = tabletPosition(screen)
        openDualHostIfNeccessary(tabletPosition)
        MultiCiceroneHolder.getRouter(tabletPosition).replaceScreen(screen)
        if (tabletPosition != TabletPosition.DIALOG) {
            onNewScreen()
        }
    }

    override fun navigateTo(screen: Screen) {
        val tabletPosition = tabletPosition(screen)
        openDualHostIfNeccessary(tabletPosition)
        MultiCiceroneHolder.getRouter(tabletPosition).navigateTo(screen)
        if (tabletPosition != TabletPosition.DIALOG) {
            onNewScreen()
        }
    }

    override fun navigateToSingleTop(screen: Screen) {
        val tabletPosition = tabletPosition(screen)
        MultiCiceroneHolder.getRouter(tabletPosition).navigateToSingleTop(screen)
        if (tabletPosition != TabletPosition.DIALOG) {
            onNewScreen()
        }
    }

    override fun openPanels(vararg screens: AppScreen) {
        newRootChain(*screens)
    }

    private fun onNewScreen() {
        MultiCiceroneHolder.getRouter(TabletPosition.DIALOG).exit()
    }

    private fun tabletPosition(screen: Screen) =
        (screen as? AppScreen)?.getTabletPosition() ?: TabletPosition.FULL

    override fun exit() {
        MultiCiceroneHolder.getRouter(TabletPosition.FULL).exit()
    }
}
