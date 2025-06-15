package com.example.tms_android

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private val bounds = RectF()
    private var progress = 0f
    private var maxProgress = 100f
    private var strokeWidth = 0f
    private var animationDuration = 1000L

    // Цвета из темы приложения
    private var progressLowColor = 0
    private var progressMediumColor = 0
    private var progressHighColor = 0
    private var backgroundColor = 0
    private var textColor = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.CircularProgressView) {
            strokeWidth = getDimension(
                R.styleable.CircularProgressView_strokeWidth,
                resources.getDimension(R.dimen.default_stroke_width)
            )

            progressLowColor = getColor(
                R.styleable.CircularProgressView_progressLowColor,
                ContextCompat.getColor(context, R.color.progress_low)
            )

            progressMediumColor = getColor(
                R.styleable.CircularProgressView_progressMediumColor,
                ContextCompat.getColor(context, R.color.progress_medium)
            )

            progressHighColor = getColor(
                R.styleable.CircularProgressView_progressHighColor,
                ContextCompat.getColor(context, R.color.progress_high)
            )

            backgroundColor = getColor(
                R.styleable.CircularProgressView_backgroundColor,
                ContextCompat.getColor(context, R.color.progress_background)
            )

            textColor = getColor(
                R.styleable.CircularProgressView_textColor,
                ContextCompat.getColor(context, R.color.text_primary)
            )

            animationDuration = getInteger(
                R.styleable.CircularProgressView_animationDuration,
                1000
            ).toLong()
        }

        backgroundPaint.color = backgroundColor
        backgroundPaint.strokeWidth = strokeWidth

        progressPaint.strokeWidth = strokeWidth

        textPaint.color = textColor
        textPaint.textSize = resources.getDimension(R.dimen.text_size_medium)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width.coerceAtMost(height) - strokeWidth) / 2f

        bounds.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        // Фон
        canvas.drawArc(bounds, 0f, 360f, false, backgroundPaint)

        // Прогресс
        val sweepAngle = 360f * (progress / maxProgress)
        progressPaint.color = getProgressColor()
        canvas.drawArc(bounds, -90f, sweepAngle, false, progressPaint)

        // Текст прогресса
        val text = "${progress.toInt()}%"
        val textY = centerY - (textPaint.descent() + textPaint.ascent()) / 2f
        canvas.drawText(text, centerX, textY, textPaint)
    }

    private fun getProgressColor(): Int {
        return when {
            progress < 30f -> progressLowColor
            progress < 70f -> progressMediumColor
            else -> progressHighColor
        }
    }

    fun setProgress(progress: Float, animate: Boolean = true) {
        val clampedProgress = progress.coerceIn(0f, maxProgress)

        if (!animate) {
            this.progress = clampedProgress
            invalidate()
            return
        }

        ValueAnimator.ofFloat(this.progress, clampedProgress).apply {
            duration = animationDuration
            interpolator = android.view.animation.AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                this@CircularProgressView.progress = animator.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = resources.getDimensionPixelSize(R.dimen.circular_progress_size)

        val width = resolveSize(desiredSize, widthMeasureSpec)
        val height = resolveSize(desiredSize, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }
}
