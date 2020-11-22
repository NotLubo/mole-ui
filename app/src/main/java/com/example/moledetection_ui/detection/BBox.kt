package com.example.moledetection_ui.detection

data class BBox(var x: Float, var width: Float, var y: Float, var height: Float){
    companion object{
        public fun fromCoords(left: Float, top: Float, right: Float, bottom:Float) : BBox{
            return BBox(left, right-left, top, bottom-top)
        }
    }
}