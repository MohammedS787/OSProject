package com.example.osproject.extension

import android.view.View
import androidx.core.view.isVisible

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/11/2023.
 */
fun View.animateFadeVisibility(isVisible: Boolean) {
    if (isVisible) {
        if (this.isVisible) return

        this.isVisible = true
        alpha = 0f
        animate().alpha(1f).setDuration(300).start()
    } else {
        if (this.isVisible.not()) return

        animate().alpha(0f).setDuration(300).withEndAction {
            visibility = View.GONE
        }.start()
    }
}