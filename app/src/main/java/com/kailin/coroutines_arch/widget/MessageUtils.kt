package com.kailin.coroutines_arch.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.kailin.coroutines_arch.R

class MessageUtils(private val context: Context) {

    private var messageDialog: Dialog? = null

    fun show(
        message: CharSequence,
        leftButtonText: CharSequence? = null,
        leftButtonClickListener: View.OnClickListener? = null,
        rightButtonText: CharSequence? = null,
        rightButtonClickListener: View.OnClickListener? = null,
    ) {
        messageDialog?.cancel()
        messageDialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(R.layout.dialog_message)
            .create()
            .also {
                it.findViewById<TextView>(R.id.message)?.text = message

                val leftButton = it.findViewById<Button>(R.id.leftButton) ?: return
                if (leftButtonText.isNullOrEmpty()) {
                    leftButton.setOnClickListener { _ -> it.dismiss() }
                } else {
                    leftButton.text = leftButtonText
                    leftButton.setOnClickListener { view ->
                        it.dismiss()
                        leftButtonClickListener?.onClick(view)
                    }
                }

                val rightButton = it.findViewById<Button>(R.id.rightButton) ?: return
                if (rightButtonText.isNullOrEmpty()) {
                    rightButton.visibility = View.GONE
                } else {
                    rightButton.visibility = View.VISIBLE
                    rightButton.text = rightButtonText
                    rightButton.setOnClickListener { view ->
                        it.dismiss()
                        rightButtonClickListener?.onClick(view)
                    }
                }
            }
        messageDialog?.show()
    }
}