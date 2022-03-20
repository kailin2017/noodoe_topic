package com.kailin.coroutines_arch.widget

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog

object DialogHelper {

    private const val TAG = "DialogHelper"

    fun msgDialog(context: Context, title: String = "", msg: String = ""): AlertDialog {
        Log.e(TAG, "msgDialog-title:$title,msg:$msg")
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .show()
    }
}