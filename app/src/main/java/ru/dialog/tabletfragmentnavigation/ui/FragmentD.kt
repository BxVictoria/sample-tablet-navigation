package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_a.btnFragmentB
import kotlinx.android.synthetic.main.fragment_a.btnFragmentC
import kotlinx.android.synthetic.main.fragment_d.*
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.navigation.DialogRouter
import ru.dialog.tabletfragmentnavigation.navigation.Views
import ru.dialog.tabletfragmentnavigation.navigation.provideRouter

class FragmentD : BaseFragment() {
    override val layout: Int = R.layout.fragment_d

    private val router: DialogRouter by lazy {
        provideRouter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFragmentB.setOnClickListener {
            router.openPanels(
                Views.FragmentAView,
                Views.FragmentBView
            )
        }
        btnFragmentC.setOnClickListener {
            router.openPanels(
                Views.FragmentAView,
                Views.FragmentCView
            )
        }
        btnFragmentDialog.setOnClickListener { router.navigateTo(Views.FragmentDialogView) }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}