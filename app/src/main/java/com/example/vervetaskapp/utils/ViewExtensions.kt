package com.example.vervetaskapp.utils

import android.view.View

fun View.visibleOrBone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
