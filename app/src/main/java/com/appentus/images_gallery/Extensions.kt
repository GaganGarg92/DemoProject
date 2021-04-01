package com.appentus.images_gallery

import android.content.Context
import android.widget.Toast

/*
* Created extension function for reuseability.
* */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}