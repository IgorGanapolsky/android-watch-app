package com.igorganapolsky.vibratingwatchapp.presentation.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorganapolsky.vibratingwatchapp.databinding.ActivityMainBinding
import com.igorganapolsky.vibratingwatchapp.util.extensions.observe
import com.igorganapolsky.vibratingwatchapp.presentation.existing_timer_info.TimerDetailsActivity
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter.ExistingTimersListAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.SetTimerActivity
import kotlinx.android.synthetic.main.activity_main.*

class ExistingTimersListActivity : AppCompatActivity(), View.OnClickListener,
    ExistingTimersListAdapter.OnItemClickListener {

    private val existingTimersListViewModel by viewModels<ExistingTimersListViewModel>()

    private lateinit var existingTimersListAdapter: ExistingTimersListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListAdapter()
        setupObservers()
    }

    private fun setupListAdapter() {
        existingTimersListAdapter = ExistingTimersListAdapter()
        existingTimersListAdapter.setItemClickListener(this)

        binding.wrvTimerList.layoutManager = LinearLayoutManager(this)
        binding.wrvTimerList.adapter = existingTimersListAdapter
        binding.addTimerButtone.setOnClickListener(this)
    }

    private fun setupObservers() {
        existingTimersListViewModel.allTimersLiveData.observe(this) { timerList ->
            existingTimersListAdapter.setData(timerList)

            if (timerList.isNotEmpty()) {
                ivTimerListImage!!.visibility = ImageView.GONE
                addTimerButtonImageLabel!!.visibility = View.GONE
                wrvTimerList!!.visibility = View.VISIBLE
            } else {
                ivTimerListImage!!.visibility = ImageView.VISIBLE
                addTimerButtonImageLabel!!.visibility = View.VISIBLE
                wrvTimerList!!.visibility = View.GONE
            }
        }
    }

    override fun onClick(view: View) {
        startActivity(Intent(this, SetTimerActivity::class.java))
    }

    override fun onItemClick(id: Int) {
        startActivity(TimerDetailsActivity.createIntent(this, id))
    }
}
