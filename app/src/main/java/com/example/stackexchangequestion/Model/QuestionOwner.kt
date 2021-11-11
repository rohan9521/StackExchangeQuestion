package com.example.stackexchangequestion.Model

data class QuestionOwner(
    var reputation:Int=0,
    var userId:String="",
    var userType:String="",
    var acceptRate:Int=0,
    var profileImageUrl:String="",
    var displayName:String="",
    var link:String=""
)