package ru.dialog.tabletfragmentnavigation.ui.host

import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.*
import ru.dialog.tabletfragmentnavigation.ui.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder


class HostFragment : BaseFragment() {

    override val layout: Int = R.layout.dialog_host

    private val tabletPosition = TabletPosition.FULL

    private val navigationHolder: NavigatorHolder by lazy {
        if (isTablet) {
            MultiCiceroneHolder.getNavigationHolder(tabletPosition)
        } else {
            SingleCiceroneHolder.INSTANCE.getNavigatorHolder()
        }
    }

    private val dialogHolder: NavigatorHolder by lazy {
        MultiCiceroneHolder.getNavigationHolder(TabletPosition.DIALOG)
    }

    private lateinit var activeNavigator: AppNavigator
    private lateinit var dialogNavigator: AppNavigator

    private val router: DialogRouter by lazy {
        provideRouter(requireContext())
    }


    private val activeFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeNavigator = AppNavigator(
            requireActivity(),
            R.id.fragment_container,
            childFragmentManager
        )
        dialogNavigator = DialogNavigator(
            requireActivity(),
            childFragmentManager
        )
    }

    override fun onResume() {
        super.onResume()
        dialogHolder.setNavigator(dialogNavigator)
        navigationHolder.setNavigator(activeNavigator)
    }

    override fun onPause() {
        super.onPause()
        dialogHolder.removeNavigator()
        navigationHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        activeFragment?.apply {
            if (!onBackPressed()) {
                if (!activeNavigator.canGoBack()) return false
                router.exit()
            }

            return true
        }
        return false
    }

    companion object {
        fun create() = HostFragment()
    }
}
