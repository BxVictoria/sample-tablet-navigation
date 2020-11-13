package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.Views
import ru.dialog.tabletfragmentnavigation.navigation.provideRouter
import ru.dialog.tabletfragmentnavigation.ui.host.HostFragment
import ru.dialog.tabletfragmentnavigation.util.isTablet
import ru.terrakok.cicerone.Router
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val hostFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment) as HostFragment?

    private val router: Router by lazy {
        provideRouter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isTablet()) {
            router.newRootChain(Views.FragmentAView, Views.FragmentBView)
        } else {
            router.newRootChain(Views.FragmentAView)
        }

        openHostDialogFragment()

    }

    override fun onBackPressed() {
        Timber.d(hostFragment.toString())
        val backHandled = when {
            else -> hostFragment?.onBackPressed()
        }

        if (backHandled != true) super.onBackPressed()
    }


    private fun openHostDialogFragment() {
        val dialogHostFragment = supportFragmentManager.findFragmentByTag(TAG)
        if (dialogHostFragment != null) return

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, HostFragment.create(), TAG)
        transaction.commit()
    }

    companion object {
        private const val TAG = "HOST"
    }
}