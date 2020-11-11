package ru.dialog.tabletfragmentnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.navigation.*
import ru.terrakok.cicerone.NavigatorHolder

class MainActivity : AppCompatActivity() {
    private val hostedFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment) as HostFragment?
    private val leftFragment
        get() = supportFragmentManager.findFragmentById(R.id.left_fragment) as HostFragment?
    private val rightFragment
        get() = supportFragmentManager.findFragmentById(R.id.right_fragment) as HostFragment?

    private val navigationHolder: NavigatorHolder by lazy {
        LocalCiceroneHolder.getNavigationHolder(TabletPosition.DIALOG)
    }

    private lateinit var activeNavigator: DialogNavigator



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // todo фулл
        if (isTablet()) {
            openHostDialogFragment(TabletPosition.LEFT)
            openHostDialogFragment(TabletPosition.RIGHT)
        } else {
            openHostDialogFragment(TabletPosition.FULL)
        }
        activeNavigator = DialogNavigator(
            this,
            R.id.container,
            supportFragmentManager
        )
        TabletRouterWrapper.newRootChain(Views.FragmentAView, Views.FragmentBView)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(activeNavigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }


    private fun openHostDialogFragment(tabletPosition: TabletPosition) {
        val tag = tabletPosition.name
        val dialogHostFragment = supportFragmentManager.findFragmentByTag(tag)
        if (dialogHostFragment != null) return

        val transaction = supportFragmentManager.beginTransaction()
        val fragmentId = when (tabletPosition) {
            TabletPosition.LEFT -> R.id.left_fragment
            TabletPosition.RIGHT -> R.id.right_fragment
            TabletPosition.FULL -> R.id.fragment
            TabletPosition.DIALOG -> TODO()
        }
        transaction.add(fragmentId, HostFragment.create(tabletPosition), tag)
        transaction.commit()
    }
}