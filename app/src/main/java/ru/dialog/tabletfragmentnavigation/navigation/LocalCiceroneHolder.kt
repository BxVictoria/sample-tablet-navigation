package ru.dialog.tabletfragmentnavigation.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Cicerone.create
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import java.util.*

/**
 * Created by terrakok 27.11.16
 */
object LocalCiceroneHolder {

    private val containers = HashMap<TabletPosition, Cicerone<Router>>()

    private fun getCicerone(tabletPosition: TabletPosition): Cicerone<Router> =
            containers.getOrPut(tabletPosition) {
                create(DialogRouter())
            }

    fun getRouter(tabletPosition: TabletPosition = TabletPosition.FULL): Router = getCicerone(tabletPosition).router

    fun getNavigationHolder(tabletPosition: TabletPosition = TabletPosition.FULL): NavigatorHolder = getCicerone(tabletPosition).navigatorHolder

}