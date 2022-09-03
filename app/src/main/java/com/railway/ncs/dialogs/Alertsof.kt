/*
package com.pha.lahore.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.pha.lahore.R
import soup.neumorphism.NeumorphButton


class Alertsof {
companion object{
    fun ShowAlertDialogWithYesNo(
        context: Context,
        string: String?,
        LeftBtnText: String?,
        rightBtnText: String?,
        btnYesListner: View.OnClickListener?,
        btnNoListner: View.OnClickListener?
    ) {
        val dialog = Dialog(context)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view: View =
            (context as FragmentActivity).layoutInflater.inflate(R.layout.custom_dialog, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        val width = context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._300sdp)
        val height = context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._190sdp)
        dialog.window!!.setLayout(width, height)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        val tvMessage: TextView
        val btnYes: NeumorphButton
        val btnNo: NeumorphButton
        tvMessage = view.findViewById(R.id.tvMessage)
        btnYes = view.findViewById(R.id.btnOkay)
        btnNo = view.findViewById(R.id.btnNo)
        tvMessage.text = string
        btnYes.setOnClickListener {
            if (btnYesListner != null) {
                dialog.dismiss()
                btnYesListner.onClick(btnYes)
            } else {
                dialog.dismiss()
            }
        }
        btnNo.setOnClickListener {
            if (btnNoListner != null) {
                dialog.dismiss()
                btnNoListner.onClick(btnNo)
            } else {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}}*/
