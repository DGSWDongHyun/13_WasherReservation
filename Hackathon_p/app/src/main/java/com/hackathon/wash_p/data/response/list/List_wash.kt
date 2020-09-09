package com.hackathon.wash_p.data.response.list

import java.util.*

data class List_wash(var grade : String?= null, var classNum : String?= null, var studentNum : String?=null, var studentName : String?=null,
                     var checkWasher : Boolean?=null, var washStartTime : Date?= null, var washEndTime : Date?=null, var floor : String?=null,
                     var washerNum : String?=null, var way : String?=null)