package com.railway.ncs.Utils

import com.railway.ncs.BuildConfig
import com.railway.ncs.Encription.Cryptography_Android
import com.railway.ncs.Encription.Cryptography_Android.getMD5




class Constant {
companion object{
    const val PREF = "com.railway.ncs"
    const val MD5Key = "x2"
    var mKey = getMD5(BuildConfig.APPLICATION_ID.toString() + MD5Key)
    val mEncDecKey: String = mKey;

   // val CRYPTKEY="e33b5c9c6c0998bc"
    val CRYPTKEY="e33b5c9c6c0998bc"

  val  CRYPTIV="NcRfUjWnZr4u7x"

}

}
