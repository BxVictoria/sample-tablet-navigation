package ru.dialog.tabletfragmentnavigation.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Cicerone.create
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import java.util.*

object MultiCiceroneHolder {

    private val containers = HashMap<TabletPosition, Cicerone<Router>>()

    private fun getCicerone(tabletPosition: TabletPosition): Cicerone<Router> =
        containers.getOrPut(tabletPosition) {
            create(DialogRouter(tabletPosition))
        }

    fun getRouter(tabletPosition: TabletPosition = TabletPosition.FULL): DialogRouter =
        getCicerone(tabletPosition).router as DialogRouter

    fun getNavigationHolder(tabletPosition: TabletPosition = TabletPosition.FULL): NavigatorHolder = getCicerone(tabletPosition).navigatorHolder

}