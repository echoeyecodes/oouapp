package com.echoeyecodes.scrub.api.model

import com.echoeyecodes.scrub.models.BarChartModel
import com.echoeyecodes.scrub.models.PercentileModel
import com.echoeyecodes.scrub.models.PieChartModel

class AnalyticsResponseModel(val gradeDistribution: PieChartModel, val gradeGroup: BarChartModel, val gradePercentile: PercentileModel)