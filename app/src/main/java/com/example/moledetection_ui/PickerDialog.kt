package com.example.moledetection_ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.moledetection_ui.db.StaticDb
import kotlinx.coroutines.NonCancellable.cancel

class PickerDialog : DialogFragment() {

    internal lateinit var listener: PickerListener

    interface PickerListener {
        fun onClick(dialog: DialogInterface, which: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as PickerListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Select skin lesion")
                .setItems(
                    StaticDb.lesions.map { l -> l.name }.toTypedArray(),
                    DialogInterface.OnClickListener { dialog, which ->
                        listener.onClick(dialog, which)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}