package com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.wear.widget.WearableRecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.adapter.HolderClickListener
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.adapter.VibrationsAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.RecyclerViewSnapLayoutManager

class SetTimerVibrationFragment : Fragment(), HolderClickListener {

    //    private var mViewModel: TimerViewModel? = null
    private var wrvVibrations: WearableRecyclerView? = null
    private var vibrationsAdapter: VibrationsAdapter? = null

    private val mViewModel by viewModels<TimerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_timer_vibration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mViewModel = ViewModelProviders.of(
//            Objects.requireNonNull<FragmentActivity>(activity),
//            ViewModelFactory.instance
//        ).get(TimerViewModel::class.java)

        setupView(view)
        setupObservers()
    }

    private fun setupView(view: View) {
        wrvVibrations = view.findViewById(R.id.wrvVibrations)

        val layoutManager =
            RecyclerViewSnapLayoutManager(activity)
        layoutManager.setItemSelectListener { pos: Int ->
            mViewModel!!.setBuzz(
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

        wrvVibrations!!.adapter = vibrationsAdapter
        wrvVibrations!!.layoutManager = layoutManager
        wrvVibrations!!.setHasFixedSize(true)

    }

    private fun setupObservers() {
        mViewModel!!.getVibrationData().observe(this) { setup ->
            wrvVibrations!!.scrollToPosition(vibrationsAdapter!!.getPosition(setup))
        }
    }

    override fun onHolderClick(position: Int) {
        wrvVibrations!!.smoothScrollToPosition(position)
    }
}
