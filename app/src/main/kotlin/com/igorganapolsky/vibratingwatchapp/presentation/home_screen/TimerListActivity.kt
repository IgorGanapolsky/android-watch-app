package com.igorganapolsky.vibratingwatchapp.presentation.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.core.extensions.observe
import com.igorganapolsky.vibratingwatchapp.presentation.details_screen.TimerDetailsActivity
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter.TimerListAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.SetTimerActivity

class TimerListActivity : AppCompatActivity(), View.OnClickListener,
    TimerListAdapter.OnItemClickListener {

//    private var mViewModel: TimerListViewModel? = null
    //    private var mViewModel by activityViewModels<TimerListViewModel>()
    private var timerListAdapter: TimerListAdapter? = null
    private var ivTimerListImage: ImageView? = null
    private var addTimerButtonImageLabel: TextView? = null
    private var wrvTimerList: WearableRecyclerView? = null

    private val mViewModel by viewModels<TimersListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val mViewModel by viewModels { ViewModelFactory(this) }
//        mViewModel = ViewModelProviders.of(this, ViewModelFactory.instance)
//            .get(TimerListViewModel::class.java)

        setupView()
        setupObservers()
    }

    private fun setupView() {
        findViewById<View>(R.id.addTimerButtonImage).setOnClickListener(this)

        ivTimerListImage = findViewById(R.id.ivTimerListImage)
        addTimerButtonImageLabel = findViewById(R.id.addTimerButtonImageLabel)
        wrvTimerList = findViewById(R.id.wrvTimerList)

        wrvTimerList!!.layoutManager = LinearLayoutManager(this)
        timerListAdapter = TimerListAdapter()
        timerListAdapter!!.setItemClickListener(this)
        wrvTimerList!!.adapter = timerListAdapter
    }

    private fun setupObservers() {
        mViewModel!!.allTimers.observe(this) { timerList ->
            timerListAdapter!!.setData(timerList)

            if (timerList != null && timerList.size > 0) {
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
