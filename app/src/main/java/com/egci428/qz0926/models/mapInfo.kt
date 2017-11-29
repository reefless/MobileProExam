package com.egci428.qz0926.models

/**
 * Created by 6272user on 11/29/17.
 */
class mapInfo (val routeName:String, val latitudeArray: ArrayList<Double>, val longtitudeArray:ArrayList<Double>, val colorArray:ArrayList<String>){
    constructor(): this("",ArrayList<Double>(),ArrayList<Double>(),ArrayList<String>())
}