package com.example.moledetection_ui.detection

internal class Prediction(var label: String, var score: Float, var bbox: BBox) :
    Comparable<Prediction> {

    override fun compareTo(other: Prediction): Int {
        return other.score.compareTo(score)
    }
}