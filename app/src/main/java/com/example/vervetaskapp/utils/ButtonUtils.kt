package com.example.vervetaskapp.utils

import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.vervetaskapp.R

fun Button.activate(active: Boolean) {
    isEnabled = active
    backgroundTintList =
        ContextCompat.getColorStateList(context, if (active) R.color.active else R.color.inactive)
}
