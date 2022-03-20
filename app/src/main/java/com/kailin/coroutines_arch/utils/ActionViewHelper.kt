package com.kailin.coroutines_arch.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object ActionViewHelper {

    @JvmStatic
    fun openStore(context: Context, packageName: String) {
        actionView(context,"https://play.google.com/store/apps/details?id=$packageName")
    }

    fun actionView(context: Context, url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            setData(Uri.parse(url))
            context.startActivity(this)
        }
    }
}