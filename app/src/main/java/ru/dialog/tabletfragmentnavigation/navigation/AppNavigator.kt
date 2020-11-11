package ru.dialog.tabletfragmentnavigation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.dialog.tabletfragmentnavigation.BaseFragment
import ru.dialog.tabletfragmentnavigation.FragmentAnimationUtils
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import timber.log.Timber

open class AppNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager? = null,
) : SupportAppNavigator(activity, fragmentManager ?: activity.supportFragmentManager, containerId) {

    init {
        this.fragmentManager
            .apply {
                addOnBackStackChangedListener {
                    val currentFragment = findFragmentById(containerId)
                    (currentFragment as? BaseFragment)?.onAddedIntoBackStack()
                }
            }
    }

    fun canGoBack(): Boolean {
        if (localStackCopy == null) {
            copyStackToLocal()
        }

        return localStackCopy.size > 0
    }

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> {
                val newCommand = Forward(command.screen)
                applyForwardCommand(newCommand)
            }
            is Replace -> {
                val newCommand = Replace(command.screen)
                super.applyCommand(newCommand)
            }
            else -> super.applyCommand(command)
        }
    }

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        Timber.d("current: $currentFragment -> next: $nextFragment")
        FragmentAnimationUtils.defaultScreenAnimation(fragmentTransaction)
    }

    override fun activityBack() = Unit

    private fun activitySlideForward(command: Forward) {
        val screen = command.screen as SupportAppScreen
        val activityIntent = screen.getActivityIntent(activity)

        // Start activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(screen, activityIntent, options)
        } else {
            executeForward(command)
        }
    }

    private fun applyForwardCommand(command: Forward) {
        when (command.screen) {
            is AppScreen -> activitySlideForward(command)
            else -> super.applyCommand(command)
        }
    }

    private fun executeForward(command: Forward) {
        val screen = command.screen as AppScreen

        val fragment = screen.getSlideFragment() ?: screen.getSwipeableFragment()
        if (fragment == null) {
            super.fragmentForward(command)
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment as Fragment,
            fragmentTransaction
        )

        fragmentTransaction
            .add(containerId, fragment)
            .addToBackStack(screen.screenKey)
            .commit()

        copyStackToLocal()
    }

    private fun copyStackToLocal() {
        applyCommands(emptyArray())
    }

    private fun checkAndStartActivity(
        screen: SupportAppScreen,
        activityIntent: Intent,
        options: Bundle?
    ) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(activityIntent, options)
        } else {
            unexistingActivity(screen, activityIntent)
        }
    }

    class ProxyAppScreen(
        private val screen: AppScreen,
        private val setup: (BaseFragment) -> Unit
    ) : AppScreen() {
        override fun getScreenKey(): String {
            return screen.getScreenKey()
        }

        override fun getActivityIntent(context: Context): Intent? {
            return screen.getActivityIntent(context)
        }

        override fun getFragment(): Fragment? {
            return screen.getFragment().also { if (it is BaseFragment) setup(it) }
        }

        override fun getSlideFragment(): SlideFragment? {
            return screen.getSlideFragment().also { if (it is BaseFragment) setup(it) }
        }
    }
}
