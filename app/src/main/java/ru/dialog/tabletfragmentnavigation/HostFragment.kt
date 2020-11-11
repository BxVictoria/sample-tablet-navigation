package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.navigation.AppNavigator
import ru.dialog.tabletfragmentnavigation.navigation.LocalCiceroneHolder
import ru.dialog.tabletfragmentnavigation.navigation.TabletPosition
import ru.terrakok.cicerone.NavigatorHolder


class HostFragment : BaseFragment() {

    override val layout: Int = R.layout.dialog_host

    private val navigationHolder: NavigatorHolder by lazy {
        LocalCiceroneHolder.getNavigationHolder(tabletPosition)
    }

    private val tabletPosition: TabletPosition by lazy {
        (arguments?.getSerializable(POSITION) as? TabletPosition) ?: TabletPosition.FULL
    }

    private lateinit var activeNavigator: AppNavigator

    private val activeFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeNavigator = AppNavigator(
            requireActivity(),
            R.id.fragment_container,
            childFragmentManager
        )
    }

    override fun onResume() {
        super.onResume()

        navigationHolder.setNavigator(activeNavigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        if (!activeNavigator.canGoBack()) return false

        activeFragment?.apply {
            onBackPressed()
            return true
        }

        return false
    }

    companion object {
        private const val POSITION = "position"

        fun create(position: TabletPosition) = HostFragment().apply {
            arguments = Bundle(1).apply {
                putSerializable(POSITION, position)
            }
        }
    }
}
