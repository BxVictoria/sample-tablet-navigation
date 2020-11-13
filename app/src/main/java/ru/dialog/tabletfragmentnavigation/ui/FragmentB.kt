package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_b.*
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.Views
import ru.dialog.tabletfragmentnavigation.navigation.provideRouter
import ru.terrakok.cicerone.Router

class FragmentB: BaseFragment() {
    override val layout: Int = R.layout.fragment_b

    private val router: Router by lazy {
        provideRouter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDialogFragment.setOnClickListener { router.navigateTo(Views.FragmentDialogView) }
    }
}