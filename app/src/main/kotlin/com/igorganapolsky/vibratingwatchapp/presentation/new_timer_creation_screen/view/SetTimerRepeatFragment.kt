package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.igorganapolsky.vibratingwatchapp.databinding.SetTimerRepeatFragmentBinding
import com.igorganapolsky.vibratingwatchapp.common.RecyclerViewSnapLayoutManager
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel.NewTimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.IHolderClickListener
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.RepeatsAdapter

class SetTimerRepeatFragment : Fragment(), IHolderClickListener {
    private val mViewModel by viewModels<NewTimerViewModel>()

    private lateinit var wearableRecyclerView: WearableRecyclerView
    private lateinit var binding: SetTimerRepeatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SetTimerRepeatFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        val adapter = RepeatsAdapter(this)
        val layoutManager =
            RecyclerViewSnapLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL
            )
        layoutManager.setOnItemSelectedListener { pos: Int -> mViewModel.setTimerRepeat(pos) }

        wearableRecyclerView = binding.wrvRepeats
        wearableRecyclerView.adapter = adapter
        wearableRecyclerView.layoutManager = layoutManager
    }

    private fun setupObservers() {
        mViewModel.getTimerData().observe(this) { wearableRecyclerView!!.smoothScrollToPosition(mViewModel.repeatPosition) }
    }

    override fun onHolderItemClick(position: Int) {
        wearableRecyclerView!!.smoothScrollToPosition(position)
    }
}
