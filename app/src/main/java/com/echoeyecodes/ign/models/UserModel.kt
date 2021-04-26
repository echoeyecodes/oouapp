package com.echoeyecodes.ign.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(val name:String, @PrimaryKey val matricNo:String, val course:String, val level:String, val gender:String, val jambScore:String)