package com.echoeyecodes.scrub.models


data class PieChartModel(val title:String, val description:String,val total:Int, val slices: List<SliceModel>)