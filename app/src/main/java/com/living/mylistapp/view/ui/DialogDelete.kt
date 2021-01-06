package com.living.mylistapp.view.ui

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.living.mylistapp.R

class DialogDelete(private val context: Context, private val ItemID: Int, private val view: onClickListenerDialog) {

    private lateinit var dialog: Dialog

    init {
        setDialog()
    }

    fun setDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.apply {
            setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
            //setGravity(Gravity.CENTER)
            setBackgroundDrawableResource(R.color.white)
        }

        dialog.findViewById<Button>(R.id.btnPositive).setOnClickListener { this.listener.onOK(dialog, ItemID) }
        dialog.findViewById<Button>(R.id.btnNegative).setOnClickListener { this.listener.onCancel(dialog) }

        dialog.show()
    }

    interface onClickListenerDialog {
        fun onOK(dialog: Dialog, itemID: Int)
        fun onCancel(dialog: Dialog)
    }

    private lateinit var listener: onClickListenerDialog
    fun setDialogListener() {
        this.listener = view;
    }
}