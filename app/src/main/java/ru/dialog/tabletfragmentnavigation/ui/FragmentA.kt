package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_a.*
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.DialogRouter
import ru.dialog.tabletfragmentnavigation.navigation.Views
import ru.dialog.tabletfragmentnavigation.navigation.provideRouter

class FragmentA : BaseFragment() {
    override val layout: Int = R.layout.fragment_a


    private val router: DialogRouter by lazy {
        provideRouter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFragmentB.setOnClickListener { router.openPanels(Views.FragmentBView) }
        btnFragmentC.setOnClickListener { router.openPanels(Views.FragmentCView) }
        btnFragmentD.setOnClickListener { router.navigateTo(Views.FragmentDView) }
    }
}