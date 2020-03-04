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
import com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.view.ExistingTimerDetailsFragment

/**
 * Screen that shows a list of existing timers.
 */
class HomeFragment : Fragment(), TimersListAdapter.OnItemClickListener {
    private lateinit var timersListAdapter: TimersListAdapter
    private var _binding: FragmentHomeBinding? = null

    private val navController by lazy { findNavController() }
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView
    private val timersListViewModel by viewModels<TimersListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListAdapter() {
        timersListAdapter = TimersListAdapter()
        timersListAdapter.setItemClickListener(this)

        binding.timersList.layoutManager = LinearLayoutManager(context)
        binding.timersList.adapter = timersListAdapter
        binding.addTimerButton.setOnClickListener {
            navController.navigate(R.id.newTimerFragment)
        }
    }

    private fun observeData() {
        timersListViewModel.allTimersLiveData.observe(viewLifecycleOwner,
            Observer { timerList ->
                timersListAdapter.setData(timerList)

                if (timerList.isEmpty()) {
                    binding.logoListImage.visibility = ImageView.VISIBLE
                    binding.addTimerButtonImageLabel.visibility = View.VISIBLE
                    binding.timersList.visibility = View.GONE
                } else {
                    binding.logoListImage.visibility = ImageView.GONE
                    binding.addTimerButtonImageLabel.visibility = View.GONE
                    binding.timersList.visibility = View.VISIBLE
                }
            }
        )
    }

    override fun onItemClick(id: Int) {
        startActivity(ExistingTimerDetailsFragment.createIntent(this, id))
    }

}
