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
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter.TimersListAdapter
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
                    this.binding.logoListImage.visibility = ImageView.VISIBLE
                    this.binding.addTimerButtonImageLabel.visibility = View.VISIBLE
                    this.binding.timersList.visibility = View.GONE
                } else {
                    this.binding.logoListImage.visibility = ImageView.GONE
                    this.binding.addTimerButtonImageLabel.visibility = View.GONE
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

    override fun onItemClick(id: Int) {
//        startActivity(ExistingTimerDetailsFragment.createIntent(this, id))
    }

}
