package com.railway.ncs.extension

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.widget.Toast
import com.pha.lahore.dialogs.ProgressBarDialog
import com.railway.ncs.R


fun Context.showProgressBar(progressBarDialogRegister: ProgressBarDialog?) {
        progressBarDialogRegister!!.setTitle(this.getString(R.string.title_progress_dialog))
        progressBarDialogRegister.setMessage(this.getString(R.string.body_progress_dialog))
        progressBarDialogRegister.setCancelable(false)
        progressBarDialogRegister.show()

}

/*fun Context.showDialog(progressBarDialogRegister: ProgressBarDialog?) {
//    progressBarDialogRegister!!.setTitle(this.getString(R.string.title_progress_dialog))
    progressBarDialogRegister?.setMessage(this.getString(R.string.confirm_logout))
    progressBarDialogRegister?.setCancelable(false)
    progressBarDialogRegister?.show()
    progressBarDialogRegister?.onClick(dialog = DialogInterface, which = Int)

}*/
fun Context.hideProgressBar( progressBarDialogRegister: ProgressBarDialog?){
    if (progressBarDialogRegister!!.isShowing){
        progressBarDialogRegister.dismiss()
    }
}
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
fun Context.bottomToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
    toast.setMargin(0F, 0F)
    toast.show()
}