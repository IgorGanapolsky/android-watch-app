package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.domain.VibrationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_new_timer_vibrations.view.*
import java.util.*

internal class VibrationsAdapter(
    private val holderClickListener: IHolderClickListener,
    private val vibTitles: Array<String>,
    private val timeTitles: Array<String>
) : RecyclerView.Adapter<VibrationsAdapter.VibrationsRecyclerViewHolder>() {

    private val vibrationsList: List<VibrationModel>?

    init {
        vibrationsList = initVibrationList()
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VibrationsRecyclerViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_new_timer_vibrations, viewGroup, false)
        return VibrationsRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(
        vibrationsRecyclerViewHolder: VibrationsRecyclerViewHolder,
        index: Int
    ) {
        vibrationsRecyclerViewHolder.bind(
            vibrationsList!![index],
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
        return try {
            vibrationsList!![position]
        } catch (exp: IndexOutOfBoundsException) {
            vibrationsList!![DEFAULT_POSITION]
        }

    }

    /**
     * Returns position o [VibrationModel] in list;
     *
     * @param setup to define position;
     * @return position of [VibrationModel] or [VibrationsAdapter.DEFAULT_POSITION]
     */
    fun getPosition(setup: VibrationModel): Int {
        return try {
            vibrationsList!!.indexOf(setup)
        } catch (exp: IndexOutOfBoundsException) {
            DEFAULT_POSITION
        }
    }

    override fun getItemCount(): Int {
        return vibrationsList?.size ?: 0
    }

    internal class VibrationsRecyclerViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(
            buzz: VibrationModel,
            vibTitles: Array<String>,
            timeTitles: Array<String>,
            index: Int,
            holderClickListener: IHolderClickListener?
        ) {
            val buzzText = vibTitles[index]
            val timeText = timeTitles[index]
            val totalString = String.format(Locale.getDefault(), "%s - %s", buzzText, timeText)

            containerView.textViewIndex.text = (index + 1).toString()
            containerView.buzzTitle.text = totalString

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
