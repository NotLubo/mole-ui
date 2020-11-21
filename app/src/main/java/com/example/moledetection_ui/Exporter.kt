package com.example.moledetection_ui

import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.annotation.RequiresApi

class Exporter {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun Export(){
        val doc = PdfDocument()
        // in postscript units, this is landscape A4 w/ 1/2" margins
        val pageInfo =
                PdfDocument.PageInfo.Builder(842, 595, 1)
                        .setContentRect(Rect(36, 36, 842-36, 595-36))
                        .create()

        val page = doc.startPage(pageInfo)
        var canvas = page.canvas


    }
}