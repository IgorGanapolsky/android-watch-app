package com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.domain.model.model.VibrationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.set_timer_vibrations_line.*
import java.util.*

internal class VibrationsAdapter(
    private val holderClickListener: HolderClickListener,
    private val vibTitles: Array<String>,
    private val timeTitles: Array<String>
) : RecyclerView.Adapter<VibrationsAdapter.VibrationsRecyclerViewHolder>() {

    private val buzzList: List<VibrationModel>?

    init {
        buzzList = initVibrationList()
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VibrationsRecyclerViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.set_timer_vibrations_line, viewGroup, false)
        return VibrationsRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(
        vibrationsRecyclerViewHolder: VibrationsRecyclerViewHolder,
        index: Int
    ) {
        vibrationsRecyclerViewHolder.bind(
            buzzList!![index],
            vibTitles, timeTitles,
            index,
            holderClickListener
        )
    }

    /**
     * Initial method for defining all [VibrationModel]
     *
     * @return setup list;
     */
    private fun initVibrationList(): List<VibrationModel> {
        val setupList = ArrayList<VibrationModel>(4)
        setupList.add(
            VibrationModel(
                VibrationModel.Type.SHORT_BUZZ,
                1,
                5
            )
        )
        setupList.add(
            VibrationModel(
                VibrationModel.Type.SHORT_BUZZ,
                3,
                3
            )
        )
        setupList.add(
            VibrationModel(
                VibrationModel.Type.SHORT_BUZZ,
                5,
                5
            )
        )
        setupList.add(
            VibrationModel(
                VibrationModel.Type.LONG_BUZZ,
                1,
                20
            )
        )
        return setupList
    }

    /**
     * Returns concrete [VibrationModel] based on position.
     *
     * @param position position of [RecyclerView.ViewHolder] in list;
     * @return concrete setup or first element in list, if setup wasn't found;
     */
    fun getBuzzByPosition(position: Int): VibrationModel {
        try {
            return buzzList!![position]
        } catch (exp: IndexOutOfBoundsException) {
            return buzzList!![DEFAULT_POSITION]
        }

    }

    /**
     * Returns position o [VibrationModel] in list;
     *
     * @param setup to define position;
     * @return position of [VibrationModel] or [VibrationsAdapter.DEFAULT_POSITION]
     */
    fun getPosition(setup: VibrationModel): Int {
        try {
            return buzzList!!.indexOf(setup)
        } catch (exp: IndexOutOfBoundsException) {
            return DEFAULT_POSITION
        }

    }

    override fun getItemCount(): Int {
        return buzzList?.size ?: 0
    }

    internal class VibrationsRecyclerViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(
            buzz: VibrationModel,
            vibTitles: Array<String>,
            timeTitles: Array<String>,
            index: Int,
            holderClickListener: HolderClickListener?
        ) {

            val buzzText = vibTitles[index]
            val timeText = timeTitles[index]
            val totalString = String.format(Locale.getDefault(), "%s - %s", buzzText, timeText)

            textViewIndex.text = (index + 1).toString()
            buzzTitle.text = totalString

            if (holderClickListener != null) {
                itemView.setOnClickListener { view ->
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        holderClickListener.onHolderItemClick(position)
                    }
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_POSITION = 0
    }
}
