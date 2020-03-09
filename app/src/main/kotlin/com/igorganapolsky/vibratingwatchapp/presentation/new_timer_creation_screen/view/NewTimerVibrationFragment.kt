package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.common.RecyclerViewSnapLayoutManager
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentNewTimerVibrationBinding
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.IHolderClickListener
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.VibrationsAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.viewmodel.NewTimerViewModel
import kotlinx.android.synthetic.main.fragment_new_timer_vibration.*

class NewTimerVibrationFragment : Fragment(), IHolderClickListener {
    private val mViewModel by viewModels<NewTimerViewModel>()

    private var vibrationsAdapter: VibrationsAdapter? = null
    private lateinit var binding: FragmentNewTimerVibrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTimerVibrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupObservers()
    }

    private fun setupViews(view: View) {
        val layoutManager =
            RecyclerViewSnapLayoutManager(
                requireContext()
            )
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

        binding.vibrationsList.adapter = vibrationsAdapter
        binding.vibrationsList.layoutManager = layoutManager
        binding.vibrationsList.setHasFixedSize(true)
    }

    private fun setupObservers() {
        mViewModel.getVibrationData().observe(this) { setup ->
            vibrationsList!!.scrollToPosition(vibrationsAdapter!!.getPosition(setup))
        }
    }

    override fun onHolderItemClick(position: Int) {
        vibrationsList!!.smoothScrollToPosition(position)
    }

}
