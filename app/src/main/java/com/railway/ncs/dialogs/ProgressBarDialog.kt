package com.pha.lahore.dialogs

import android.app.ActionBar
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.railway.ncs.R


class ProgressBarDialog(context: Context) : BaseAlertDialog(context),
    DialogInterface.OnClickListener {

    private val progressBar: ProgressBar
    private val message: TextView
    private val noteTV: TextView? = null

    fun setTitle(title: String?) {
        super.setTitle(title)
    }

    override fun setMessage(message: CharSequence?) {
        this.message.text = message
    }

     fun setButton(type: Int, text: String?) {
        setButton(type, text, this)
    }

    fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun setNoteMessage(message: String) {
        noteTV!!.visibility = View.VISIBLE
        noteTV.text = "*Note: $message"
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            BUTTON_POSITIVE -> dismissManually()
            BUTTON_NEGATIVE -> {
                val isSuccess = true
                if (!isSuccess) {
                    dismissManually()
                    //					((StartupActivity)context).finish();
                }
            }
            else -> {
            }
        }
    }

    fun setWidth(width: Int) {
        val width1 = width
    }

    override fun show() {
        super.show()
        window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
    }

    override fun dismiss() {
        super.dismiss()
    }

    private fun dismissManually() {
        super.dismiss()
    }

    init {
        val context1 = context
        val factory = LayoutInflater.from(context)
        val progressBarView: View = factory.inflate(R.layout.dialog_progressbar, null)
        setView(progressBarView)
        progressBar = progressBarView.findViewById(R.id.progressBar)
        message = progressBarView.findViewById(R.id.progressMessage)
        this.setCanceledOnTouchOutside(false)
    }
}
