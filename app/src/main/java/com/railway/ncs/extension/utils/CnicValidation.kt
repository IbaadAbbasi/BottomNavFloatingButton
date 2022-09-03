package com.railway.ncs.extension.utils

fun isValidCnic(value: String): Boolean {
    var re13 = Regex("^[0-9+]{5}[0-9+]{7}[0-9]{1}\$")
    var re11 = Regex("^[0-9+]{5}[0-9+]{5}[0-9]{1}\$")
    if (value.matches(re13) || value.matches(re11)) {
        val a = value.substring(0, 1)
        val b = value.substring(1, 2)
        val c = value.substring(2, 3)
        val d = value.substring(3, 4)
        val e = value.substring(4, 5)
        val a1 = value.substring(5, 6)
        val b1 = value.substring(6, 7)
        val c1 = value.substring(7, 8)
        val d1 = value.substring(8, 9)
        val e1 = value.substring(9, 10)
        val firstDigit = a.toInt()
        if (firstDigit <= 0 || firstDigit >= 8) {
            return false
        } else if (a.contains(b) && b.contains(c) && c.contains(d) && d.contains(e)
            || a1.contains(b1) && b1.contains(c1) && c1.contains(d1) && d1.contains(e1)) {
            return  false
        }
        return  true

    } else {
        return  false
    }
}
