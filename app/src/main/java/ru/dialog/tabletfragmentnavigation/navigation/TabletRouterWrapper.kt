package ru.dialog.tabletfragmentnavigation.navigation

import android.content.Context
import ru.dialog.tabletfragmentnavigation.isTablet
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

fun provideRouter(context: Context): Router = if (context.isTablet()) TabletRouterWrapper else CiceroneHolder.INSTANCE.getRouter()

object TabletRouterWrapper : Router() {

    override fun newRootChain(vararg screens: Screen) {
        val screensByPosition = screens.asList()
            .groupBy { tabletPosition(it) }
        for (position in screensByPosition.keys) {
            LocalCiceroneHolder.getRouter(position)
                .newRootChain(*screensByPosition.getValue(position).toTypedArray())
        }
        if (!screensByPosition.keys.contains(TabletPosition.DIALOG)) {
            onNewScreen()
        }
    }

    override fun newChain(vararg screens: Screen) {
        val screensByPosition = screens.asList()
            .groupBy { tabletPosition(it) }
        for (position in screensByPosition.keys) {
            LocalCiceroneHolder.getRouter(position)
                .newChain(*screensByPosition.getValue(position).toTypedArray())
        }
        if (!screensByPosition.keys.contains(TabletPosition.DIALOG)) {
            onNewScreen()
        }
    }

    override fun navigateTo(screen: Screen) {
        val tabletPosition = tabletPosition(screen)
        LocalCiceroneHolder.getRouter(tabletPosition).navigateTo(screen)
        if (tabletPosition != TabletPosition.DIALOG) {
            onNewScreen()
        }
    }

    private fun onNewScreen() {
        LocalCiceroneHolder.getRouter(TabletPosition.DIALOG).exit()
    }

    private fun tabletPosition(screen: Screen) =
        (screen as? AppScreen)?.getTabletPosition() ?: TabletPosition.FULL

    override fun exit() {
        //подумать обсудить
        LocalCiceroneHolder.getRouter(TabletPosition.LEFT).exit()
    }
}
