package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.common.IStepTouchActionListener
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentNewTimerTimeBinding
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.viewmodel.NewTimerViewModel
import com.triggertrap.seekarc.SeekArc
import java.util.*

class NewTimerTimeFragment : Fragment(), View.OnClickListener, SeekArc.OnSeekArcChangeListener {
    private val mViewModel by viewModels<NewTimerViewModel>()

    private var activeColor: Int = 0
    private var inactiveColor: Int = 0
    private lateinit var stepTouchActionListener: IStepTouchActionListener

    private lateinit var binding: FragmentNewTimerTimeBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        stepTouchActionListener = activity as NewTimerTWizardFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTimerTimeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activeColor = ContextCompat.getColor(requireContext(), R.color.white_active)
        inactiveColor = ContextCompat.getColor(requireContext(), R.color.white_inactive)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.seekArc.setOnSeekArcChangeListener(this)
    }

    private fun setupObservers() {
        mViewModel.getCountdownData().observe(
            Objects.requireNonNull<FragmentActivity>(activity),
            Observer<TimeMeasurement> { this.setSelection(it) })
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
        stepTouchActionListener.onActionStart()
    }

    override fun onStopTrackingTouch(seekArc: SeekArc) {
        stepTouchActionListener.onActionEnd()
    }

    private fun setSelection(selection: TimeMeasurement) {
        binding.seekArc.progress = mViewModel.calculateProgress()

        binding.tvLabelMeasure.text = selection.shortcut
        binding.tvLabelMeasure.text =
            String.format(Locale.ENGLISH, "%d", mViewModel.currentTimeValue)

        when (selection) {
            TimeMeasurement.HOURS -> {
                binding.tvHours.setTextColor(activeColor)
                binding.tvMinutes.setTextColor(inactiveColor)
                binding.tvSeconds.setTextColor(inactiveColor)
            }
            TimeMeasurement.MINUTES -> {
                binding.tvMinutes.setTextColor(activeColor)
                binding.tvHours.setTextColor(inactiveColor)
                binding.tvSeconds.setTextColor(inactiveColor)
            }
            TimeMeasurement.SECONDS -> {
                binding.tvSeconds.setTextColor(activeColor)
                binding.tvHours.setTextColor(inactiveColor)
                binding.tvMinutes.setTextColor(inactiveColor)
            }
        }
    }
}
