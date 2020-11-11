package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import ru.dialog.tabletfragmentnavigation.navigation.AppNavigator
import ru.dialog.tabletfragmentnavigation.navigation.AppScreen


class HostDialogFragment : BaseDialogFragment() {

    override val layout: Int = R.layout.dialog_host

    private val childScreen: AppScreen by lazy {
        arguments?.getParcelable(SCREEN) as? AppScreen ?: throw IllegalArgumentException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, childScreen.fragment!!)
            .commit()
    }

    companion object {
        private const val SCREEN = "SCREEN"

        fun create(childScreen: AppScreen) = HostDialogFragment().apply {
            arguments = Bundle(1).apply {
                putParcelable(SCREEN, childScreen)
            }
        }
    }
}

