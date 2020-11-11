package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_a.*
import ru.dialog.tabletfragmentnavigation.navigation.TabletRouterWrapper

class FragmentA: BaseFragment() {
    override val layout: Int = R.layout.fragment_a

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFragmentB.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentBView) }
        btnFragmentC.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentCView) }
        btnFragmentD.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentDView) }
    }

}