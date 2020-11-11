package ru.dialog.tabletfragmentnavigation

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

import java.util.Collections
import java.util.WeakHashMap

abstract class BaseFragment : Fragment() {

    abstract val layout: Int

    protected open val hideKeyboardAfterCreate = true

    private var toolbar: Toolbar? = null

    private val viewCreatedLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    protected val viewCreatedLifecycleOwner = LifecycleOwner { viewCreatedLifecycle }
    open val needToSaveKeyboardAfterRotate = false

    //region Status Bar Animations
    open var makeStatusBarDarkerAnimator: ValueAnimator? = null
    open var makeStatusBarLighterAnimator: ValueAnimator? = null

    open fun initStatusBarAnimators(context: Context, window: Window) {
        val primaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val shadowedColor = ContextCompat.getColor(context, R.color.statusbar_shadowed_color)
        val interpolator = DecelerateInterpolator()

        makeStatusBarDarkerAnimator = StatusBarCommonAnimator.create(primaryColor, shadowedColor, window)
        makeStatusBarDarkerAnimator?.interpolator = interpolator

        makeStatusBarLighterAnimator = StatusBarCommonAnimator.create(shadowedColor, primaryColor, window)
        makeStatusBarLighterAnimator?.interpolator = interpolator

        addToAnimators(makeStatusBarDarkerAnimator)
        addToAnimators(makeStatusBarLighterAnimator)
    }
    //endregion

    private val viewAnimators = Collections.newSetFromMap(
            WeakHashMap<ViewPropertyAnimator, Boolean>()
    )
    private val animators = Collections.newSetFromMap(
            WeakHashMap<Animator, Boolean>()
    )

    protected lateinit var homeActivity: AppCompatActivity


    private var metricsSent: Boolean = false
    private var metricsTimeCreated: Long = 0

    private var openKeyboardAttempts = 0

    /**
     * Event that means a fragment was recorded into backstack of fragments
     */
    open fun onAddedIntoBackStack() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        viewAnimators.forEach {
            it.cancel()
        }
        animators.forEach {
            it.cancel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layout, null).also {
                if (hideKeyboardAfterCreate) hideKeyboard()
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreatedLifecycle.currentState = Lifecycle.State.STARTED
        initStatusBarAnimators(requireContext(), requireActivity().window)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar = null
        viewCreatedLifecycle.currentState = Lifecycle.State.DESTROYED
    }

    open fun onBackPressed(): Boolean {
        backPressAction()
        return true
    }

    open fun backPressAction() {
        hideKeyboard()
    }

    open fun toolbarBackPressAction() {
        backPressAction()
    }

    fun addToAnimators(animator: ViewPropertyAnimator?) {
        animator?.let {
            viewAnimators.add(it)
        }
    }

    fun addToAnimators(animator: Animator?) {
        animator?.let {
            animators.add(it)
        }
    }


    fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            homeActivity = context
        }
    }

    fun initToolbar(toolbar: View) {
        initToolbar(toolbar as Toolbar)
    }

    fun invalidateOptionsMenu() {
        val inflater = activity?.menuInflater
        val toolbar = toolbar
        if (inflater != null && toolbar != null) onCreateOptionsMenu(toolbar.menu, inflater)
    }

    fun initToolbar(toolbar: Toolbar, isShowNavBackBtn: Boolean = true) {
        this.toolbar = toolbar
        toolbar.setOnMenuItemClickListener(::onOptionsItemSelected)
        if (isShowNavBackBtn) {
            toolbar.navigationIcon = AppCompatResources.getDrawable(homeActivity, R.drawable.ic_arrow_left_branding_24)
            toolbar.setNavigationOnClickListener { toolbarBackPressAction() }
        } else {
            toolbar.navigationIcon = null
        }
    }

    fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    fun setToolbarTitle(@StringRes title: Int) {
        toolbar?.setTitle(title)
    }

    fun setToolbarSubtitle(title: String) {
        toolbar?.subtitle = title
    }

    fun setToolbarSubtitle(@StringRes title: Int) {
        toolbar?.setSubtitle(title)
    }
}
