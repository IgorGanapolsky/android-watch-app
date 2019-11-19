package com.igorganapolsky.vibratingwatchapp.presentation

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

typealias OnItemSelectedListener = (Int) -> Unit

class RecyclerViewSnapLayoutManager private constructor(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var helper: LinearSnapHelper
    private lateinit var onItemSelectedListener: OnItemSelectedListener

    constructor(context: Context) : this(context, VERTICAL, false)
    constructor(context: Context, orientation: Int) : this(context, orientation, false)

    override fun onAttachedToWindow(recyclerView: RecyclerView) {
        super.onAttachedToWindow(recyclerView)
        this.recyclerView = recyclerView

        helper = LinearSnapHelper()
        helper.attachToRecyclerView(recyclerView)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.dispatchSetSelected(false)
            val position = findPosition()
            onItemSelected(position)
        }
    }

    private fun findPosition(): Int {
        val snapView = helper.findSnapView(this)
        return if (snapView != null) {
            snapView.isSelected = true
            recyclerView.getChildAdapterPosition(snapView)
        } else {
            0
        }
    }

    fun setOnItemSelectedListener(itemSelectListener: OnItemSelectedListener) {
        this.onItemSelectedListener = itemSelectListener
    }

    private fun onItemSelected(pos: Int) {
        onItemSelectedListener.invoke(pos)

    }

}
