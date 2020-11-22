package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.moledetection_ui.db.Lesion
import com.example.moledetection_ui.db.StaticDb

class new_page_1 : AppCompatActivity(), PickerDialog.PickerListener, NameInputDialog.NameListener {
    lateinit var mBodyView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_1)

        StaticDb.initialize(this.applicationContext)

        val updateBtn = findViewById<Button>(R.id.button_updateWorkflow)
        updateBtn.isEnabled = StaticDb.lesions.size > 0
        updateBtn.setOnClickListener {
            val dialog = PickerDialog()
            dialog.show(supportFragmentManager, "LesionPicker")
        }

        findViewById<Button>(R.id.button_addNew).setOnClickListener {
            val dialog = NameInputDialog()
            dialog.show(supportFragmentManager, "NameDialog")
        }
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        StaticDb.currentLesion = StaticDb.lesions[which]
        val intent = Intent(this, ViewLesion::class.java)
        startActivity(intent)
    }

    override fun onNameEntered(name: String) {
        StaticDb.currentLesion = Lesion(name, null)
        val intent = Intent(this, new_page_2::class.java)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        findViewById<Button>(R.id.button_updateWorkflow).isEnabled = StaticDb.lesions.size > 0
    }

}