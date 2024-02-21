package com.example.snphmsapo.model

import android.icu.text.NumberFormat
import java.util.*


object Number {
    fun Double.formatNumber(): String {
        if (this != 0.0) {
            val formatter = NumberFormat.getInstance(Locale.US)
            formatter.maximumFractionDigits = 10
            return formatter.format(this)
        }
        return "0"
    }
}
