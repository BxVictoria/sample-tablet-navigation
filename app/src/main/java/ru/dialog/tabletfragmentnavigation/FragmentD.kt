package ru.dialog.tabletfragmentnavigation

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_b.*
import ru.dialog.tabletfragmentnavigation.navigation.TabletRouterWrapper

class FragmentD: BaseFragment() {
    override val layout: Int = R.layout.fragment_d

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDialogFragment.setOnClickListener { TabletRouterWrapper.navigateTo(Views.FragmentDialogView) }
    }

}