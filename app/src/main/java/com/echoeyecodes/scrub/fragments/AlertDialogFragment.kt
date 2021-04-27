package com.echoeyecodes.scrub.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.echoeyecodes.scrub.MainActivity
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.callbacks.MainActivityCallBack
import com.google.android.material.button.MaterialButton

class AlertDialogFragment : DialogFragment(){

    private lateinit var cancelBtn:MaterialButton
    private lateinit var confirmBtn:MaterialButton
    private lateinit var mainActivityCallBack: MainActivityCallBack

    companion object{
        const val TAG = "ALERT_DIALOG_FRAGMENT"
        fun newInstance() = AlertDialogFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity){
            mainActivityCallBack = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        dialog?.window?.setGravity(Gravity.TOP or Gravity.LEFT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = inflater.inflate(R.layout.fragment_alert_message, container, false)

        cancelBtn = view.findViewById(R.id.cancel_btn)
        confirmBtn = view.findViewById(R.id.confirm_btn)

        cancelBtn.setOnClickListener { dismiss() }
        confirmBtn.setOnClickListener { mainActivityCallBack.removeAccount() }

        return view
    }

    override fun onStart() {
        super.onStart()
//        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}