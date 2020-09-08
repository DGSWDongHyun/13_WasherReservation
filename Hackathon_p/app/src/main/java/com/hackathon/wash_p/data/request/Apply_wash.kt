package com.hackathon.wash_p.data.request

import java.util.*

data class Apply_wash(var grade_ : String?= null, var class_ : String?= null, var class_num : String?=null, var name_ : String?=null,
                      var isUsing : Boolean?=null, var time_start : Date?= null, var time_end : Date?=null, var floor : String?=null,
                      var washing_num : String?=null, var direction : String?=null)