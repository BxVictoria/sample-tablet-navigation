package ru.dialog.tabletfragmentnavigation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.dialog.tabletfragmentnavigation.HostDialogFragment
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import java.lang.ref.WeakReference


class DialogNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager? = null,
) : AppNavigator(activity, containerId, fragmentManager ?: activity.supportFragmentManager) {


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