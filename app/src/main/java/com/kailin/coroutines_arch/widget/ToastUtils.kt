package com.kailin.coroutines_arch.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ToastUtils(private val context: Context) {

    private var toast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())

    fun showLong(text: String?) = makeText(text, Toast.LENGTH_LONG)

    fun showShort(text: String?) = makeText(text, Toast.LENGTH_SHORT)

    private fun makeText(text: CharSequence?, duration: Int) {
        text ?: return
        toast?.cancel()
        toast = Toast.makeText(context, text, duration).also { it.show() }
        handler.postDelayed({ toast = null }, 5000)
    }
}