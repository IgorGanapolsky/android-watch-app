package com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.SetTimerActivity
import com.igorganapolsky.vibratingwatchapp.core.IStepActionListener
import com.triggertrap.seekarc.SeekArc
import java.util.*

class SetTimerTimeFragment : Fragment(), View.OnClickListener, SeekArc.OnSeekArcChangeListener {
    //    private var mViewModel: TimerViewModel? = null
    private var seekArc: SeekArc? = null
    private var activeColor: Int = 0
    private var inactiveColor: Int = 0
    private var tvLabel: TextView? = null
    private var tvLabelMeasure: TextView? = null
    private var tvHours: TextView? = null
    private var tvMinutes: TextView? = null
    private var tvSeconds: TextView? = null
    private var actionListener: IStepActionListener? = null

    private val mViewModel by viewModels<TimerViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionListener = activity as SetTimerActivity?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_timer_time_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mViewModel = ViewModelProviders.of(
//            Objects.requireNonNull<FragmentActivity>(activity),
//            ViewModelFactory.instance
//        ).get(TimerViewModel::class.java)

        setupView(view)
        setupObservers()
    }

    private fun setupView(view: View) {
        activeColor = ContextCompat.getColor(requireContext(), R.color.white_active)
        inactiveColor = ContextCompat.getColor(requireContext(), R.color.white_inactive)

        tvLabel = view.findViewById(R.id.tvLabel)
        tvLabelMeasure = view.findViewById(R.id.tvLabelMeasure)

        tvHours = view.findViewById(R.id.tvHours)
        tvHours!!.setOnClickListener(this)
        tvMinutes = view.findViewById(R.id.tvMinutes)
        tvMinutes!!.setOnClickListener(this)
        tvSeconds = view.findViewById(R.id.tvSeconds)
        tvSeconds!!.setOnClickListener(this)

        seekArc = view.findViewById(R.id.seekArc)
        seekArc!!.setOnSeekArcChangeListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvHours -> mViewModel.setSelection(TimeMeasurement.HOURS)
            R.id.tvMinutes -> mViewModel.setSelection(TimeMeasurement.MINUTES)
            R.id.tvSeconds -> mViewModel.setSelection(TimeMeasurement.SECONDS)
        }
    }

    override fun onProgressChanged(seekArc: SeekArc, progress: Int, byUser: Boolean) {
        if (byUser) {
            mViewModel.setTimeSelection(progress)
        }
    }

    override fun onStartTrackingTouch(seekArc: SeekArc) {
        actionListener!!.onActionStart()
    }

    override fun onStopTrackingTouch(seekArc: SeekArc) {
        actionListener!!.onActionEnd()
    }

    private fun setupObservers() {
        mViewModel.getCountdownData().observe(
            Objects.requireNonNull<FragmentActivity>(activity),
            Observer<TimeMeasurement> { this.setSelection(it) })
    }

    private fun setSelection(selection: TimeMeasurement) {
        seekArc!!.progress = mViewModel.calculateProgress()

        tvLabelMeasure!!.text = selection.shortcut
        tvLabel!!.text = String.format(Locale.ENGLISH, "%d", mViewModel.currentTimeValue)

        when (selection) {
            TimeMeasurement.HOURS -> {
                tvHours!!.setTextColor(activeColor)
                tvMinutes!!.setTextColor(inactiveColor)
                tvSeconds!!.setTextColor(inactiveColor)
            }
            TimeMeasurement.MINUTES -> {
                tvMinutes!!.setTextColor(activeColor)
                tvHours!!.setTextColor(inactiveColor)
                tvSeconds!!.setTextColor(inactiveColor)
            }
            TimeMeasurement.SECONDS -> {
                tvSeconds!!.setTextColor(activeColor)
                tvHours!!.setTextColor(inactiveColor)
                tvMinutes!!.setTextColor(inactiveColor)
            }
        }
    }
}

