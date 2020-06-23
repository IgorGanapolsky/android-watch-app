package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentHomeBinding
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.model.*
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel.TimersListViewModel

/**
 * Screen that shows a list of existing timers.
 */
class HomeFragment : Fragment(), TimersListAdapter.OnItemClickListener {
    private lateinit var timersListAdapter: TimersListAdapter
    private lateinit var binding: FragmentHomeBinding

    private val navController by lazy { findNavController() }
    private val timersListViewModel by viewModels<TimersListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        observeUi()

        return this.binding.root
    }

    private fun observeUi() {
        setupListAdapter()

        timersListViewModel.allTimers.observe(viewLifecycleOwner,
            Observer { timerList ->
                timersListAdapter.setData(timerList)

                if (timerList.isEmpty()) {
                    this.binding.topLogo.visibility = ImageView.VISIBLE
                    this.binding.addTimerButton.visibility = View.VISIBLE
                    this.binding.addTimerText.visibility = View.VISIBLE
                    this.binding.timersList.visibility = View.GONE
                } else {
                    this.binding.topLogo.visibility = ImageView.GONE
                    this.binding.addTimerButton.visibility = View.GONE
                    this.binding.addTimerText.visibility = View.GONE
                    this.binding.timersList.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun setupListAdapter() {
        timersListAdapter = TimersListAdapter()
        timersListAdapter.setItemClickListener(this)

        this.binding.timersList.layoutManager = LinearLayoutManager(context)
        this.binding.timersList.adapter = timersListAdapter
        this.binding.addTimerButton.setOnClickListener {
            navController.navigate(R.id.newTimerFragment)
        }
    }

    override fun renderViewState(viewStateAction: TimersListAction) = when (viewStateAction) {
        HitPlayPause -> ::println
        is GoTimerDetails -> ::println
        is LongPress -> ::println
        is SwipeToDelete -> ::println
    }

}
