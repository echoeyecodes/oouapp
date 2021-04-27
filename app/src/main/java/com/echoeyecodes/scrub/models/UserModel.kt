package com.echoeyecodes.scrub.models

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.*

@Entity(tableName = "users")
data class UserModel(val name:String, @PrimaryKey val matricNo:String, val course:String, val level:String, val gender:String, val jambScore:String, val timestamp:String, val thumbnail:String){

    @SuppressLint("SimpleDateFormat")
    fun getTimeDifference() : String{

        try{
            val dateformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestamp)
            val start = Date(dateformat!!.time).toInstant()
            val end = Instant.now()

            val duration = (Duration.between(start, end)).toMillis()
            return converter(duration)
        }catch(exception:Exception){
            return "N/A"
        }

    }

    private fun converter(date: Long): String {
        val days = date / 1000 / 60 / 60 / 24
        val hours = date / 1000 / 60 / 60
        val minutes = date / 1000 / 60
        val seconds = date / 1000

        return when {
            days >= 1 -> {
                "$days day(s) ago"
            }
            hours in 1..24 -> {
                "$hours hour(s) ago"
            }
            minutes in 1..60 -> {
                "$minutes minutes ago"
            }
            seconds in 0..60 -> {
                "a few second(s) ago"
            }
            else -> {
                "a while ago"
            }
        }
    }
}