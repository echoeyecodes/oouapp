package com.echoeyecodes.ign.api.model

import com.echoeyecodes.ign.models.CourseModel
import com.echoeyecodes.ign.models.UserModel

data class StudentResponseModel(val bio:UserModel, val results:List<CourseModel>)