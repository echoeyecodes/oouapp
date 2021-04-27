package com.echoeyecodes.scrub.api.model

import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.UserModel

data class StudentResponseModel(val bio:UserModel, val results:List<CourseModel>)