package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorganapolsky.vibratingwatchapp.databinding.ActivityMainBinding
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentHomeBinding
import com.igorganapolsky.vibratingwatchapp.presentation.existing_timer_screen.view.ExistingTimerDetailsFragment
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter.HomeTimersListAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel.ExistingTimersListViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.SetTimerActivity

/**
 * Screen that shows a list of existing timers.
 */
class HomeTimersListFragment : Fragment(), View.OnClickListener,
    HomeTimersListAdapter.OnItemClickListener {

    // TODO: Igor - convert this class to fragment!!!
    private val existingTimersListViewModel by viewModels<ExistingTimersListViewModel>()

    private lateinit var homeTimersListAdapter: HomeTimersListAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListAdapter()
        observeData()
    }

    private fun setupListAdapter() {
        homeTimersListAdapter = HomeTimersListAdapter()
        homeTimersListAdapter.setItemClickListener(this)

        binding.wrvTimerList.layoutManager = LinearLayoutManager(this)
        binding.wrvTimerList.adapter = homeTimersListAdapter
        binding.addTimerButtone.setOnClickListener(this)
    }

    private fun observeData() {
        existingTimersListViewModel.allTimersLiveData.observe(this) { timerList ->
            homeTimersListAdapter.setData(timerList)

            if (timerList.isNotEmpty()) {
                binding.ivTimerListImage.visibility = ImageView.GONE
                binding.addTimerButtonImageLabel.visibility = View.GONE
                binding.wrvTimerList.visibility = View.VISIBLE
            } else {
                binding.ivTimerListImage.visibility = ImageView.VISIBLE
                binding.addTimerButtonImageLabel.visibility = View.VISIBLE
                binding.wrvTimerList.visibility = View.GONE
            }
        }
    }

    override fun onClick(view: View) {
        startActivity(Intent(this, SetTimerActivity::class.java))
    }

    override fun onItemClick(id: Int) {
        startActivity(ExistingTimerDetailsFragment.createIntent(this, id))
    }
}
