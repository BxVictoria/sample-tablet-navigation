package ru.dialog.tabletfragmentnavigation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.dialog.tabletfragmentnavigation.ui.host.HostDialogFragment
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward


class DialogNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager? = null,
) : AppNavigator(activity, 0, fragmentManager ?: activity.supportFragmentManager) {


    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> openDialog(command.screen as AppScreen)
            is Back -> dismissDialogs()
            else -> super.applyCommand(command)
        }
    }

    fun dismissDialogs() {
        fragmentManager.fragments.forEach {
            (it as? DialogFragment)?.dismissAllowingStateLoss()
        }
    }

    private fun openDialog(screen: AppScreen) {
        val dialog = HostDialogFragment.create(screen)
        dialog.show(fragmentManager, "dialog")
    }

}