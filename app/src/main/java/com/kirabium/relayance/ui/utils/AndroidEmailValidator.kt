package com.kirabium.relayance.ui.utils

import android.util.Patterns
import javax.inject.Inject

class AndroidEmailValidator @Inject constructor() {

    fun validate(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}