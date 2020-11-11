package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_dialog.*
import ru.dialog.tabletfragmentnavigation.navigation.TabletRouterWrapper

class FragmentDialog: BaseFragment() {
    override val layout: Int = R.layout.fragment_dialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFragmentB.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentBView) }
        btnFragmentC.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentCView) }
        btnFragmentD.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentDView) }
        btnDialogFragment.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentDialogView) }
    }
}