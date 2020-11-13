package ru.dialog.tabletfragmentnavigation.ui

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import ru.dialog.tabletfragmentnavigation.slidr.Slidr
import ru.dialog.tabletfragmentnavigation.slidr.model.*
import ru.dialog.tabletfragmentnavigation.slidr.widget.SliderPanel


interface SlideFragment {
    fun slidrWrapContainer(fragmentView: View?): View?

    fun slidrAttach(container: View?, @IdRes rootId: Int, fragmentManager: FragmentManager)

    fun slidrDetach()

    fun slidrLock()

    fun slidrUnlock()

    fun getSlideListener(): SlidrListener = SlidrListenerAdapter()

    fun getSlideSensitivity() = 0.1f

    open class Delegate : SlideFragment {
        private val isSwipeBackEnabled = false

        private var sliderInterface: SlidrInterface? = null

        override fun slidrWrapContainer(fragmentView: View?): View? {
            if (fragmentView == null)
                return null

            if (!isSwipeBackEnabled)
                return fragmentView

            val context = fragmentView.context
            val slidrFrameLayout = FrameLayout(context)
            val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )

            slidrFrameLayout.layoutParams = params
            slidrFrameLayout.background = ContextCompat.getDrawable(context, android.R.color.transparent)
            slidrFrameLayout.addView(fragmentView)
            return slidrFrameLayout
        }

        override fun slidrAttach(container: View?, rootId: Int, fragmentManager: FragmentManager) {
            if (!isSwipeBackEnabled || container == null)
                return

            initSlider(container, rootId, fragmentManager)
        }

        override fun slidrDetach() {
            if (!isSwipeBackEnabled)
                return

            sliderInterface = null
        }

        override fun slidrLock() {
            sliderInterface?.lock()
        }

        override fun slidrUnlock() {
            sliderInterface?.unlock()
        }

        private fun initSlider(container: View?, rootId: Int, fragmentManager: FragmentManager) {
            if (sliderInterface == null) {
                val slider = (container as ViewGroup).children.firstOrNull()

                slider?.let {
                    if (slider is SliderPanel) {
                        sliderInterface = slider.defaultInterface
                        sliderInterface?.unlock()
                    } else {
                        slider.findViewById<View>(rootId)?.let {
                            sliderReplace(it, rootId, fragmentManager)
                        }
                    }
                }
            }
        }

        private fun sliderReplace(container: View, rootId: Int, fragmentManager: FragmentManager) {
            val config = SlidrConfig.Builder()
                    .position(SlidrPosition.LEFT)
                    .sensitivity(getSlideSensitivity())
                    .scrimColor(Color.BLACK)
                    .listener(getSlideListener())
                    .scrimStartAlpha(0.2f)
                    .scrimEndAlpha(0f)
                    .velocityThreshold(2400F)
                    .distanceThreshold(0.25f)
                    .edge(false)
                    .ignoreChildScroll(false)
                    .build()

            sliderInterface = Slidr.replace(container, config, rootId, fragmentManager)
        }
    }
}
