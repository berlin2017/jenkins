package com.berlin.lsrefresh

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class JDRefreshHeader :FrameLayout,RefreshHeader{

    private lateinit var rotate_up: Animation
    private lateinit var rotate_down: Animation
    private lateinit var rotate_infinite: Animation

    private lateinit var headerText: TextView
    private lateinit var loading: ImageView
    private lateinit var loading_pre: LinearLayout

    override fun reset() {
        loading_pre.visibility = View.VISIBLE
        loading.visibility = View.GONE
        headerText.text = "下拉刷新"
    }

    override fun pull() {

    }

    override fun refreshing() {
        headerText.text = "正在刷新"
        loading_pre.visibility = View.GONE
        loading.visibility = View.VISIBLE
        loading.setImageResource(R.drawable.jd_loading)
        val mAnimationDrawable = loading.drawable as AnimationDrawable
        mAnimationDrawable.start()
    }

    override fun onPositionChange(
        currentPos: Float,
        lastPos: Float,
        refreshPos: Float,
        isTouch: Boolean,
        state: State
    ) {
        Log.e("currentPos", currentPos.toString())
        Log.e("lastPos", lastPos.toString())
        Log.e("refreshPos", refreshPos.toString())
        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {
            if (isTouch && state === State.PULL) {
                headerText.text = "下拉刷新"
            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state === State.PULL) {
                headerText.text = "释放立即刷新"
            }
        }
        var sccla = currentPos / measuredHeight
        if (currentPos >= measuredHeight) {
            sccla = 1f
        }
        loading_pre.scaleX = sccla
        loading_pre.scaleY = sccla
    }

    override fun complete() {
        headerText.text = "刷新成功"
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        rotate_up = AnimationUtils.loadAnimation(context, R.anim.rotate_up)
        rotate_down = AnimationUtils.loadAnimation(context, R.anim.rotate_down)
        rotate_infinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite)

        View.inflate(context, R.layout.jd_header_refresh_layout, this)

        headerText = findViewById(R.id.header_text) as TextView
        loading = findViewById(R.id.loading) as ImageView
        loading_pre = findViewById(R.id.loading_pre) as LinearLayout
    }

}