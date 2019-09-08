package com.igorganapolsky.vibratingwatchapp.presentation

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSnapLayoutManager private constructor(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {
    private var recyclerView: RecyclerView? = null
    private var helper: LinearSnapHelper? = null
    private var itemSelectListener: OnItemSelectListener? = null

    constructor(context: Context) : this(context, LinearLayoutManager.VERTICAL, false) {}

    constructor(context: Context, orientation: Int) : this(context, orientation, false) {}

    override fun onAttachedToWindow(recyclerView: RecyclerView?) {
        super.onAttachedToWindow(recyclerView)
        this.recyclerView = recyclerView

        helper = LinearSnapHelper()
        helper!!.attachToRecyclerView(recyclerView)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView!!.dispatchSetSelected(false)
            val position = findPosition()

            if (itemSelectListener != null) {
                itemSelectListener!!.onItemSelected(position)
            }
        }
    }

    private fun findPosition(): Int {
        val snapView = helper!!.findSnapView(this)
        if (snapView != null) {
            snapView.isSelected = true
            return recyclerView!!.getChildAdapterPosition(snapView)
        } else {
            return 0
        }
    }

    fun setItemSelectListener(itemSelectListener: OnItemSelectListener) {
        this.itemSelectListener = itemSelectListener
    }

    interface OnItemSelectListener {
        fun onItemSelected(position: Int)
    }
}
