package com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.databinding.SetTimerVibrationFragmentBinding
import com.igorganapolsky.vibratingwatchapp.presentation.RecyclerViewSnapLayoutManager
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.NewTimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.adapter.HolderClickListener
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.adapter.VibrationsAdapter
import kotlinx.android.synthetic.main.set_timer_vibration_fragment.*

class SetTimerVibrationFragment : Fragment(), HolderClickListener {
    private val mViewModel by viewModels<NewTimerViewModel>()

    private var vibrationsAdapter: VibrationsAdapter? = null
    private lateinit var binding: SetTimerVibrationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SetTimerVibrationFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupObservers()
    }

    private fun setupViews(view: View) {
        val layoutManager = RecyclerViewSnapLayoutManager(requireContext())
        layoutManager.setOnItemSelectedListener { pos: Int ->
            mViewModel.setBuzz(
                vibrationsAdapter!!.getBuzzByPosition(
                    pos
                )
            )
        }

        vibrationsAdapter = VibrationsAdapter(
            this,
            resources.getStringArray(R.array.buzzes),
            resources.getStringArray(R.array.times)
        )

        binding.wrvVibrations!!.adapter = vibrationsAdapter
        binding.wrvVibrations!!.layoutManager = layoutManager
        binding.wrvVibrations!!.setHasFixedSize(true)
    }

    private fun setupObservers() {
        mViewModel.getVibrationData().observe(this) { setup ->
            wrvVibrations!!.scrollToPosition(vibrationsAdapter!!.getPosition(setup))
        }
    }

    override fun onHolderItemClick(position: Int) {
        wrvVibrations!!.smoothScrollToPosition(position)
    }
}
