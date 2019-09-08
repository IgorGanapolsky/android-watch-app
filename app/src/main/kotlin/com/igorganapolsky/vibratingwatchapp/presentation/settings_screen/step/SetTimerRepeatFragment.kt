package com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.adapter.HolderClickListener
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.adapter.RepeatsAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.RecyclerViewSnapLayoutManager

class SetTimerRepeatFragment : Fragment(), HolderClickListener {

    private var wrvRepeats: WearableRecyclerView? = null
    //    private var mViewModel: TimerViewModel? = null
    private val mViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mViewModel = ViewModelProviders.of(requireActivity(), ViewModelFactory.instance)
//            .get(TimerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.set_timer_repeat_fragment, container, false)
        setupView(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupView(rootView: View) {
        wrvRepeats = rootView.findViewById(R.id.wrvRepeats)

        val adapter = RepeatsAdapter(this)
        val layoutManager =
            RecyclerViewSnapLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL
            )
        layoutManager.setItemSelectListener { pos: Int -> mViewModel.setTimerRepeat(pos) }

        wrvRepeats!!.adapter = adapter
        wrvRepeats!!.layoutManager = layoutManager
    }

    private fun setupObservers() {
        mViewModel.getTimerData()
            .observe(this) { wrvRepeats!!.smoothScrollToPosition(mViewModel.repeatPosition) }
    }

    override fun onHolderClick(position: Int) {
        wrvRepeats!!.smoothScrollToPosition(position)
    }
}
