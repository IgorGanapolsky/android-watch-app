package com.igorganapolsky.vibratingwatchapp.presentation.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorganapolsky.vibratingwatchapp.databinding.ActivityMainBinding
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter.TimerListAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.SetTimerActivity
import com.igorganapolsky.vibratingwatchapp.presentation.timer_info_screen.TimerDetailsActivity
import com.igorganapolsky.vibratingwatchapp.other.extensions.observe
import kotlinx.android.synthetic.main.activity_main.*

class TimerListActivity : AppCompatActivity(), View.OnClickListener, TimerListAdapter.OnItemClickListener {
    private val mViewModel by viewModels<TimersListViewModel>()

    private lateinit var timerListAdapter: TimerListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        timerListAdapter = TimerListAdapter()
        timerListAdapter.setItemClickListener(this)

        binding.wrvTimerList!!.layoutManager = LinearLayoutManager(this)
        binding.wrvTimerList!!.adapter = timerListAdapter
        binding.addTimerButtonImage.setOnClickListener(this)
    }

    private fun setupObservers() {
        mViewModel.allTimers.observe(this) { timerList ->
            timerListAdapter.setData(timerList)

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
