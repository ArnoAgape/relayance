package com.kirabium.relayance.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateExt {

    companion object {
        fun Date.toHumanDate(): String {
            val calendar = Calendar.getInstance()
            calendar.time = this
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }
}