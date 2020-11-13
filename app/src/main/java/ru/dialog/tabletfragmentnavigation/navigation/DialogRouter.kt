package ru.dialog.tabletfragmentnavigation.navigation

import androidx.lifecycle.MutableLiveData
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import java.util.*

open class DialogRouter(val tabletPosition: TabletPosition) : Router() {
    private val screenStack = Stack<Screen>()
    private val topScreenMutableLiveData = MutableLiveData<Screen>()

    fun newChainFrom(backTo: Screen, vararg screen: Screen) {
        val commandList = mutableListOf<Command>()
        commandList.add(BackTo(backTo))
        for (element in screen) {
            commandList.add(Forward(element))
        }

        executeCommands(*commandList.toTypedArray())
    }

    fun backWithSlider(screen: Screen) {
        if (screenStack.isNotEmpty()) {
            var stackChanged = false
            if (screenStack.size >= 2) {
                // сейчас просто смотрим предыдущий экран, метод работает только для messaging и своего рода костыль
                val scrn = screenStack[screenStack.size - 2]
                if (scrn.screenKey == screen.screenKey) {
                    screenStack.pop()
                    stackChanged = true
                }
                if (stackChanged) {
                    topScreenMutableLiveData.postValue(screen)
                }
            }
        }
    }

    override fun newRootChain(vararg screens: Screen) {
        super.newRootChain(*screens)
        if (screenStack.isNotEmpty()) {
            screenStack.clear()
        }
        screens.forEach {
            screenStack.push(it)
        }
        topScreenMutableLiveData.postValue(screenStack.peek())
    }

    override fun backTo(screen: Screen?) {
        super.backTo(screen)
        while (screenStack.isNotEmpty() && screenStack.peek() != screen) {
            screenStack.pop()
        }
        topScreenMutableLiveData.postValue(screenStack.peek())
    }

    override fun newRootScreen(screen: Screen) {
        super.newRootScreen(screen)
        if (screenStack.isNotEmpty()) {
            screenStack.clear()
        }
        screenStack.push(screen)
        topScreenMutableLiveData.postValue(screen)
    }

    override fun navigateTo(screen: Screen) {
        super.navigateTo(screen)
        screenStack.add(screen)
        topScreenMutableLiveData.postValue(screen)
    }

    open fun navigateToSingleTop(screen: Screen) {
        executeCommands(ForwardSingleTop(screen))
        screenStack.add(screen)
        topScreenMutableLiveData.postValue(screen)
    }

    override fun finishChain() {
        super.finishChain()
        if (screenStack.isNotEmpty()) {
            screenStack.clear()
        }
    }

    override fun exit() {
        super.exit()
        if (screenStack.isNotEmpty()) {
            screenStack.pop()
        }
        if (screenStack.isNotEmpty()) {
            topScreenMutableLiveData.postValue(screenStack.peek())
        }
    }

    override fun replaceScreen(screen: Screen) {
        super.replaceScreen(screen)
        if (screenStack.isNotEmpty()) {
            screenStack.pop()
        }
        screenStack.push(screen)
        topScreenMutableLiveData.postValue(screen)
    }

    open fun openPanels(vararg screens: AppScreen) {
        newChain(*screens)
    }


    public override fun executeCommands(vararg commands: Command) {
        super.executeCommands(*commands)
    }
}
