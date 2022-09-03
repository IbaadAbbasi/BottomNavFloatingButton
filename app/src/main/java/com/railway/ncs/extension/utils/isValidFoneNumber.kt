package com.railway.ncs.extension.utils

 fun isValidPhoneNumber(value:String):Boolean{
     val jazz = "030"
     val zong = "031"
     val ufone = "033"
     val warid = "032"
     val telenor = "034"
     return if (value.length == 11) {
         (value.startsWith(jazz) || value.startsWith(zong) || value.startsWith(ufone)
                 || value.startsWith(warid) || value.startsWith(telenor))
     } else {
         false
     }
 }