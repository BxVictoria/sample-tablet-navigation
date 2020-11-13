package ru.dialog.tabletfragmentnavigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dialog.tabletfragmentnavigation.util.hideKeyboard
import ru.dialog.tabletfragmentnavigation.util.isTablet

abstract class BaseFragment : Fragment() {

    abstract val layout: Int

    val isTablet: Boolean by lazy {
        requireContext().isTablet()
    }

    /**
     * Event that means a fragment was recorded into backstack of fragments
     */
    open fun onAddedIntoBackStack() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layout, null)

    open fun onBackPressed(): Boolean {
        backPressAction()
        return false
    }

    open fun backPressAction() {
        hideKeyboard()
    }
}
