package im.dlg.core_ui.component.swipe_back

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import im.dlg.core_ui.component.swipe_back.SwipeBackLayout.OnFinishListener
import ru.dialog.tabletfragmentnavigation.R
import ru.dialog.tabletfragmentnavigation.ui.BaseFragment


abstract class SwipeBackFragment : BaseFragment(), SwipeBackLayout.SwipeBackListener {
    private lateinit var swipeBackLayout: SwipeBackLayout
    private lateinit var ivShadow: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val swipeBackContainer = createContainer()
        swipeBackLayout.addView(view)

        return swipeBackContainer
    }

    private fun createContainer(): View {
        val container = FrameLayout(requireContext())
        swipeBackLayout = SwipeBackLayout(requireContext())
        swipeBackLayout.setDragEdge(DEFAULT_DRAG_EDGE)
        swipeBackLayout.setOnSwipeBackListener(this)
        swipeBackLayout.setOnFinishListener(object : OnFinishListener {
            override fun onFinishState() {
                onBackPressed()
            }
        })

        ivShadow = ImageView(requireContext())
        ivShadow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black_alpha_50))

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        container.addView(ivShadow, params)
        container.addView(swipeBackLayout)
        return container
    }

    fun setDragDirectMode(dragDirectMode: SwipeBackLayout.DragDirectMode) {
        swipeBackLayout.setDragDirectMode(dragDirectMode)
    }

    override fun onViewPositionChanged(fractionAnchor: Float, fractionScreen: Float) {
        ivShadow.alpha = 1 - fractionScreen
    }

    companion object {
        private val DEFAULT_DRAG_EDGE = SwipeBackLayout.DragEdge.LEFT
    }
}
