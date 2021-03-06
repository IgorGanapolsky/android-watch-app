package com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentTimerDeleteBinding
import com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.viewmodel.ExistingTimerViewModel

class ExistingTimerDeleteDialogFragment : Fragment(), View.OnClickListener {
    private val mViewModel by viewModels<ExistingTimerViewModel>()

    private lateinit var binding: FragmentTimerDeleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerDeleteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewCancel.setOnClickListener(this)
        binding.imageViewApprove.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.image_view_approve -> {
                mViewModel.deleteTimer()
                activity!!.finish()
            }
            R.id.image_view_cancel -> fragmentManager!!.popBackStack()
        }
    }
}
