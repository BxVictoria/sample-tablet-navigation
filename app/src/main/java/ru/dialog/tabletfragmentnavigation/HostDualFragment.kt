package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.navigation.AppNavigator
import ru.dialog.tabletfragmentnavigation.navigation.LocalCiceroneHolder
import ru.dialog.tabletfragmentnavigation.navigation.TabletPosition
import ru.terrakok.cicerone.NavigatorHolder


class HostDualFragment : BaseFragment() {

    override val layout: Int = R.layout.fragment_host_dual

    private val leftNavigationHolder: NavigatorHolder by lazy {
        LocalCiceroneHolder.getNavigationHolder(TabletPosition.LEFT)
    }
    private val rightNavigationHolder: NavigatorHolder by lazy {
        LocalCiceroneHolder.getNavigationHolder(TabletPosition.RIGHT)
    }

    private lateinit var leftNavigator: AppNavigator
    private lateinit var rightNavigator: AppNavigator

    private val leftFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.left_fragment) as? BaseFragment

    private val rightFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.right_fragment) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftNavigator = AppNavigator(
            requireActivity(),
            R.id.left_fragment,
            childFragmentManager
        )
        rightNavigator = AppNavigator(
            requireActivity(),
            R.id.right_fragment,
            childFragmentManager
        )
    }

    override fun onResume() {
        super.onResume()

        leftNavigationHolder.setNavigator(leftNavigator)
        rightNavigationHolder.setNavigator(rightNavigator)
    }

    override fun onPause() {
        super.onPause()
        leftNavigationHolder.removeNavigator()
        rightNavigationHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        if (rightNavigator.canGoBack()) {
            rightFragment?.apply {
                onBackPressed()
                return true
            }
        }
        if (leftNavigator.canGoBack()) {
            leftFragment?.apply {
                onBackPressed()
                return true
            }
        }

        return false
    }

    companion object {
        fun create() = HostDualFragment()
    }
}
