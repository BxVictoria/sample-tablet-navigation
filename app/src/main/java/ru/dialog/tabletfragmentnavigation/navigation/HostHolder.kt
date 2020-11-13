package ru.dialog.tabletfragmentnavigation.navigation

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command
import java.util.*

class HostHolder : NavigatorHolder {
    private var navigator: Navigator? = null
    private val pendingCommands: Queue<Array<Command>> = LinkedList()

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        while (!pendingCommands.isEmpty()) {
            if (this.navigator != null) {
                executeCommands(pendingCommands.poll())
            } else break
        }
    }

    override fun removeNavigator() {
        navigator = null
    }

    fun executeCommands(commands: Array<Command>) {
        if (navigator != null) {
            navigator!!.applyCommands(commands)
        } else {
            pendingCommands.add(commands)
        }
    }
}