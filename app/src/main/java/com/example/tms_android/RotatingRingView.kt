package com.example.tms_android
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class RotatingRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.BLUE
        strokeCap = Paint.Cap.ROUND
    }

    private val rectF = RectF()
    private var rotationAngle = 0f
    private val rotationSpeed = 7f // Градусов на кадр

    private var isAnimating = false
    private val frameDuration = 16L // +-60fps

    init {
        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(width, height) / 2f - paint.strokeWidth

        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        canvas.drawArc(rectF, rotationAngle, 120f, false, paint)
    }

    private fun startAnimation() {
        if (isAnimating) return

        isAnimating = true
        post(object : Runnable {
            override fun run() {
                if (!isAnimating) return

                rotationAngle += rotationSpeed

                if (rotationAngle >= 360) rotationAngle = 0f

                invalidate()
                postDelayed(this, frameDuration)
            }
        })
    }

    fun start() {
        if (!isAnimating) {
            isAnimating = true
            startAnimation()
        }
    }

    fun stop() {
        isAnimating = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = min(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(size, size)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }
}