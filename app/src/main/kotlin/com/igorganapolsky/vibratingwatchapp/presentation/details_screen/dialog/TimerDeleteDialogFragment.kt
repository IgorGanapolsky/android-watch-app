package com.igorganapolsky.vibratingwatchapp.presentation.details_screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.presentation.details_screen.TimerDetailsViewModel

class TimerDeleteDialogFragment : Fragment(), View.OnClickListener {

//    private var mViewModel: TimerDetailsViewModel? = null
    private val mViewModel by viewModels<TimerDetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_delete_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mViewModel = ViewModelProviders.of(
//            Objects.requireNonNull<FragmentActivity>(activity),
//            ViewModelFactory.instance
//        ).get(TimerDetailsViewModel::class.java)

        view.findViewById<View>(R.id.ivCancel).setOnClickListener(this)
        view.findViewById<View>(R.id.ivApprove).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivApprove -> {
                mViewModel!!.deleteTimer()
                activity!!.finish()
            }
            R.id.ivCancel -> fragmentManager!!.popBackStack()
        }
    }
}
