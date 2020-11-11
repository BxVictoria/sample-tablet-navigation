package ru.dialog.tabletfragmentnavigation.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder

enum class CiceroneHolder {
    INSTANCE;

    private val cicerone = Cicerone.create(DialogRouter())

    fun getNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    fun getRouter(): DialogRouter {
        return cicerone.router
    }
}