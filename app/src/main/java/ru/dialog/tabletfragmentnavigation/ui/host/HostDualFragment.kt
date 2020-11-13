package ru.dialog.tabletfragmentnavigation.ui.host

import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.AppNavigator
import ru.dialog.tabletfragmentnavigation.navigation.MultiCiceroneHolder
import ru.dialog.tabletfragmentnavigation.navigation.TabletPosition
import ru.dialog.tabletfragmentnavigation.ui.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder


class HostDualFragment : BaseFragment() {

    override val layout: Int = R.layout.fragment_host_dual

    private val leftNavigationHolder: NavigatorHolder by lazy {
        MultiCiceroneHolder.getNavigationHolder(TabletPosition.LEFT)
    }
    private val rightNavigationHolder: NavigatorHolder by lazy {
        MultiCiceroneHolder.getNavigationHolder(TabletPosition.RIGHT)
    }
//    private val dialogHolder: NavigatorHolder by lazy {
//        MultiCiceroneHolder.getNavigationHolder(TabletPosition.DIALOG)
//    }

    private lateinit var leftNavigator: AppNavigator
    private lateinit var rightNavigator: AppNavigator
//    private lateinit var dialogNavigator: AppNavigator

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
//        dialogNavigator = DialogNavigator(
//                requireActivity(),
//        childFragmentManager
//        )
    }

    override fun onResume() {
        super.onResume()
//        dialogHolder.setNavigator(dialogNavigator)
        leftNavigationHolder.setNavigator(leftNavigator)
        rightNavigationHolder.setNavigator(rightNavigator)
    }

    override fun onPause() {
        super.onPause()
        leftNavigationHolder.removeNavigator()
        rightNavigationHolder.removeNavigator()
//        dialogHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        if (rightNavigator.canGoBack()) {
            rightFragment?.apply {
                if (!onBackPressed()) {
                    MultiCiceroneHolder.getRouter(TabletPosition.RIGHT).exit()
                }
                return true
            }
        }
        if (leftNavigator.canGoBack()) {
            leftFragment?.apply {
                if (!onBackPressed()) {
                    MultiCiceroneHolder.getRouter(TabletPosition.LEFT).exit()
                }
                return true
            }
        }

        return false
    }

    companion object {
        fun create() = HostDualFragment()
    }
}
