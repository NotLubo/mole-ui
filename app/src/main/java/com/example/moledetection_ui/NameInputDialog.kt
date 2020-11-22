package com.example.moledetection_ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NameInputDialog : DialogFragment() {

    internal lateinit var listener: NameListener

    interface NameListener {
        fun onNameEntered(name: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as NameListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Enter name for lesion")
            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("OK", DialogInterface.OnClickListener  { _, _ ->
                listener.onNameEntered(input.text.toString())
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener  { _, _ ->  })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}