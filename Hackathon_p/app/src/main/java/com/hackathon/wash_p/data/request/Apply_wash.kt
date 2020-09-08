package com.hackathon.wash_p.data.request

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Apply_wash(var floor : String?= null, var washerNum: String?= null, var way : String?=null, var grade : String?=null,
                      var classNum : String?=null, var studentNum : String?= null, var studentName : String?=null, var checkWasher : Boolean?=null,
                      var washStartTime : String?=null, var washEndTime : String?=null)