package tn.yassin.discovery.Utils


import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import tn.yassin.discovery.R
import kotlin.math.abs

class ZoomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    companion object {
        // We can be in one of these 3 states
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val CLICK = 3
    }

    private var matri: Matrix? = null
    private var mode = NONE

    // Remember some things for zooming
    private var minScale = 1f
    private var maxScale = 3f
    private var viewWidth = 0
    private var viewHeight = 0
    private var saveScale = 1f
    private var origWidth = 0f
    private var origHeight = 0f
    private var oldMeasuredWidth = 0
    private var oldMeasuredHeight = 0

    private lateinit var floats: FloatArray

    init {
        attrs?.let { _attrs ->
            val typedArray = context.obtainStyledAttributes(
                _attrs, R.styleable.ZoomImageView
            )
            try {
                maxScale = typedArray.getFloat(R.styleable.ZoomImageView_setMaxZoom, maxScale)
                setMaxZoom(maxScale)
                initEventListener(context)
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        // Rescales image on rotation
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight || viewWidth == 0 || viewHeight == 0) return
        oldMeasuredHeight = viewHeight
        oldMeasuredWidth = viewWidth

        if (saveScale == 1f) {
            //Fit to screen.
            val scale: Float
            val drawable = drawable
            if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) return
            val bmWidth = drawable.intrinsicWidth
            val bmHeight = drawable.intrinsicHeight
            Log.d("bmSize", "bmWidth: $bmWidth bmHeight : $bmHeight")
            val scaleX = viewWidth.toFloat() / bmWidth.toFloat()
            val scaleY = viewHeight.toFloat() / bmHeight.toFloat()
            scale = scaleX.coerceAtMost(scaleY)
            matri?.setScale(scale, scale)

            // Center the image
            var redundantYSpace = viewHeight.toFloat() - scale * bmHeight.toFloat()
            var redundantXSpace = viewWidth.toFloat() - scale * bmWidth.toFloat()
            redundantYSpace /= 2.toFloat()
            redundantXSpace /= 2.toFloat()
            matri?.postTranslate(redundantXSpace, redundantYSpace)
            origWidth = viewWidth - 2 * redundantXSpace
            origHeight = viewHeight - 2 * redundantYSpace
            imageMatrix = matri
        }
        fixTrans()
    }

    private fun initEventListener(context: Context) {
        super.setClickable(true)
        super.setFocusable(true)

        val last = PointF()
        val start = PointF()
        val scaleDetector = ScaleGestureDetector(context, ScaleListener())

        matri = Matrix()
        floats = FloatArray(9)
        imageMatrix = matri
        scaleType = ScaleType.MATRIX

        setOnTouchListener { _, event ->
            scaleDetector.onTouchEvent(event)
            val curr = PointF(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last.set(curr)
                    start.set(last)
                    mode = DRAG
                }
                MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                    val deltaX = curr.x - last.x
                    val deltaY = curr.y - last.y
                    val fixTransX =
                        getFixDragTrans(deltaX, viewWidth.toFloat(), origWidth * saveScale)
                    val fixTransY =
                        getFixDragTrans(deltaY, viewHeight.toFloat(), origHeight * saveScale)
                    matri?.postTranslate(fixTransX, fixTransY)
                    fixTrans()
                    last[curr.x] = curr.y
                }
                MotionEvent.ACTION_UP -> {
                    mode = NONE
                    val xDiff = abs(curr.x - start.x).toInt()
                    val yDiff = abs(curr.y - start.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK) performClick()
                }
                MotionEvent.ACTION_POINTER_UP -> mode = NONE
            }
            imageMatrix = matri
            invalidate()

            true // indicate event was handled
        }
    }

    private fun getFixTrans(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) {
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }

        if (trans < minTrans) return -trans + minTrans
        if (trans > maxTrans) return -trans + maxTrans

        return 0f
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return when {
            contentSize <= viewSize -> 0f
            else -> delta
        }
    }

    private fun fixTrans() {
        matri?.getValues(floats)
        val transX = floats[Matrix.MTRANS_X]
        val transY = floats[Matrix.MTRANS_Y]
        val fixTransX = getFixTrans(transX, viewWidth.toFloat(), origWidth * saveScale)
        val fixTransY = getFixTrans(transY, viewHeight.toFloat(), origHeight * saveScale)
        if (fixTransX != 0f || fixTransY != 0f) matri?.postTranslate(fixTransX, fixTransY)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var scaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= scaleFactor

            if (saveScale > maxScale) {
                saveScale = maxScale
                scaleFactor = maxScale / origScale

            } else if (saveScale < minScale) {
                saveScale = minScale
                scaleFactor = minScale / origScale
            }

            if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight) matri?.postScale(
                scaleFactor,
                scaleFactor,
                viewWidth / 2.toFloat(),
                viewHeight / 2.toFloat()
            ) else matri?.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)

            fixTrans()
            return true
        }
    }

    fun setMaxZoom(x: Float) {
        maxScale = x
    }
}