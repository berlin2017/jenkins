package com.berlin.lslibrary.refresh

interface RefreshHeader {
    /**
     * 松手，头部隐藏后会回调这个方法
     */
    fun reset()

    /**
     * 下拉出头部的一瞬间调用
     */
    fun pull()

    /**
     * 正在刷新的时候调用
     */
    fun refreshing()

    /**
     * 头部滚动的时候持续调用
     * @param currentPos target当前偏移高度
     * @param lastPos   target上一次的偏移高度
     * @param refreshPos 可以松手刷新的高度
     * @param isTouch   手指是否按下状态（通过scroll自动滚动时需要判断）
     * @param state     当前状态
     */
    fun onPositionChange(currentPos: Float, lastPos: Float, refreshPos: Float, isTouch: Boolean, state: State)

    /**
     * 刷新成功的时候调用
     */
    fun complete()
}