package com.appentus.images_gallery

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constants {
    const val USER_MSG_KEY = "user_message"

    /*
    * Used to check network connection.
    * */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}