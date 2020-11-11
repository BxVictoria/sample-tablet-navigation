package im.dlg.core_ui.component.swipe_back

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.dialog.tabletfragmentnavigation.R


abstract class SwipeBackActivity : AppCompatActivity(), SwipeBackLayout.SwipeBackListener {

    private lateinit var swipeBackLayout: SwipeBackLayout
    private lateinit var ivShadow: ImageView

    private var onCenterGravitySwipeViewListener: OnCenterGravitySwipeView? = null

    private var isCenterGravity = true

    override fun setContentView(layoutResID: Int) {
        super.setContentView(createContainer())
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        swipeBackLayout.addView(view)
    }

    private fun createContainer(): View {
        val container = FrameLayout(this)
        swipeBackLayout = SwipeBackLayout(this)
        swipeBackLayout.setDragEdge(DEFAULT_DRAG_EDGE)
        swipeBackLayout.setOnSwipeBackListener(this)
        ivShadow = ImageView(this)
        ivShadow.setBackgroundColor(ContextCompat.getColor(this, R.color.black_alpha_50))
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        container.addView(ivShadow, params)
        container.addView(swipeBackLayout)
        return container
    }

    fun setOnCenterGravitySwipeView(listener: OnCenterGravitySwipeView) {
        onCenterGravitySwipeViewListener = listener
    }

    fun setOnFinishListener(onFinishListener: SwipeBackLayout.OnFinishListener) {
        swipeBackLayout.setOnFinishListener(onFinishListener)
    }

    fun setDragDirectMode(dragDirectMode: SwipeBackLayout.DragDirectMode) {
        swipeBackLayout.setDragDirectMode(dragDirectMode)
    }

    override fun onViewPositionChanged(fractionAnchor: Float, fractionScreen: Float) {
        when {
            isCenterGravity && fractionAnchor != 0f && fractionScreen != 0f -> {
                isCenterGravity = false
                onCenterGravitySwipeViewListener?.onCenterGravity(isCenterGravity)
            }

            !isCenterGravity && fractionAnchor == 0f && fractionScreen == 0f -> {
                isCenterGravity = true
                onCenterGravitySwipeViewListener?.onCenterGravity(isCenterGravity)
            }
        }

        ivShadow.alpha = 1 - fractionScreen
    }

    interface OnCenterGravitySwipeView {
        fun onCenterGravity(isCenterGravity: Boolean)
    }

    override fun onDestroy() {
        onCenterGravitySwipeViewListener = null
        super.onDestroy()
    }

    companion object {
        private val DEFAULT_DRAG_EDGE = SwipeBackLayout.DragEdge.LEFT
    }
}