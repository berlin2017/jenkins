package com.berlin.lslibrary.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import com.berlin.lslibrary.R

class SimpleRefreshHeader : FrameLayout, RefreshHeader {

    private lateinit var rotate_up: Animation
    private lateinit var rotate_down: Animation
    private lateinit var rotate_infinite: Animation
    private lateinit var textView: TextView
    private lateinit var arrowIcon: View
    private lateinit var successIcon: View
    private lateinit var loadingIcon: View

    override fun reset() {
        textView.text = "下拉刷新"
        successIcon.visibility = View.INVISIBLE
        arrowIcon.visibility = View.VISIBLE
        arrowIcon.clearAnimation()
        loadingIcon.visibility = View.INVISIBLE
        loadingIcon.clearAnimation()
    }

    override fun pull() {

    }

    override fun refreshing() {
        arrowIcon.visibility = View.INVISIBLE
        loadingIcon.visibility = View.VISIBLE
        textView.text = "正在刷新"
        arrowIcon.clearAnimation()
        loadingIcon.startAnimation(rotate_infinite)
    }

    override fun onPositionChange(
        currentPos: Float,
        lastPos: Float,
        refreshPos: Float,
        isTouch: Boolean,
        state: State
    ) {
        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {
            if (isTouch && state === State.PULL) {
                textView.text = "下拉刷新"
                arrowIcon.clearAnimation()
                arrowIcon.startAnimation(rotate_down)
            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state === State.PULL) {
                textView.text = "释放立即刷新"
                arrowIcon.clearAnimation()
                arrowIcon.startAnimation(rotate_up)
            }
        }
    }

    override fun complete() {
        loadingIcon.visibility = View.INVISIBLE
        loadingIcon.clearAnimation()
        successIcon.visibility = View.VISIBLE
        textView.text = "刷新成功"
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        rotate_up = AnimationUtils.loadAnimation(context, R.anim.rotate_up)
        rotate_down = AnimationUtils.loadAnimation(context, R.anim.rotate_down)
        rotate_infinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite)

        View.inflate(context, R.layout.header_ls_simple, this)

        textView = findViewById(R.id.text) as TextView
        arrowIcon = findViewById(R.id.arrowIcon)
        successIcon = findViewById(R.id.successIcon)
        loadingIcon = findViewById(R.id.loadingIcon)
    }

}