package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_dialog.*
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.Views
import ru.dialog.tabletfragmentnavigation.navigation.provideRouter
import ru.terrakok.cicerone.Router

class FragmentDialog : BaseFragment() {
    override val layout: Int = R.layout.fragment_dialog

    private val router: Router by lazy {
        provideRouter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFragmentB.setOnClickListener { router.navigateTo(Views.FragmentBView) }
        btnFragmentC.setOnClickListener { router.navigateTo(Views.FragmentCView) }
        btnFragmentDialog.setOnClickListener { router.navigateTo(Views.FragmentDView) }
        btnDialogFragment.setOnClickListener { router.navigateTo(Views.FragmentDialogView) }
    }
}