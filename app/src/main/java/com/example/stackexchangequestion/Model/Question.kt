package com.example.stackexchangequestion.Model

import java.util.*
import kotlin.collections.ArrayList


data class Question(
    var owner:QuestionOwner?=null,
    var isAnswered:Boolean=false,
    var viewCount:Int=0,
    var answerCount:Int=0,
    var score:Int=0,
    var lastActiveDate: String="",
    var creationDate: String="",
    var lastEditDate: String="",
    var questionId:String="",
    var contentLicense:String="",
    var link:String="",
    var title:String="",
    var tags:ArrayList<String> = arrayListOf()
)