package com.example.newseveryday

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.stackexchangequestion.Model.Question
import com.example.stackexchangequestion.Model.QuestionOwner
import com.example.stackexchangequestion.utilities.*
import java.security.acl.Owner
import java.text.SimpleDateFormat
import java.util.*

class QuestionViewModel:ViewModel() {
    private val url = "https://api.stackexchange.com/2.2/questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow"
    private var _questionList = MutableLiveData<MutableList<Question>>()
    val questionList: LiveData<MutableList<Question>>
        get() = _questionList

    fun fetchData(context: Context){
        var volley = Volley.newRequestQueue(context)

        var requestData = JsonObjectRequest(Request.Method.GET,url,null, {
                jsonObjectOne-> run{
            var listNewsObject: MutableList<Question> = mutableListOf()
            var jsonObject = jsonObjectOne.getJSONArray("items")

            for (i in 0 until jsonObject.length()) {
                Log.d("list",jsonObject.getJSONObject(i).toString())
                var question = Question()
                if(jsonObject.getJSONObject(i).has(TAG_TITLE))
                    question.title          = jsonObject.getJSONObject(i).getString("title")

                if(jsonObject.getJSONObject(i).has(TAG_IS_ANSWERED))
                    question.isAnswered     = jsonObject.getJSONObject(i).getBoolean(TAG_IS_ANSWERED)

                if(jsonObject.getJSONObject(i).has(TAG_ANSWER_COUNT))
                    question.answerCount    = jsonObject.getJSONObject(i).getInt(TAG_ANSWER_COUNT)

                if(jsonObject.getJSONObject(i).has(TAG_LINK))
                    question.link           = jsonObject.getJSONObject(i).getString(TAG_LINK)

                if(jsonObject.getJSONObject(i).has(TAG_SCORE))
                    question.score          = jsonObject.getJSONObject(i).getInt(TAG_SCORE)

                if(jsonObject.getJSONObject(i).has(TAG_ANSWER_COUNT))
                    question.answerCount    = jsonObject.getJSONObject(i).getInt(TAG_ANSWER_COUNT)
                val calendar = Calendar.getInstance()
                if(jsonObject.getJSONObject(i).has(TAG_CREATION_DATE)) {
                    calendar.timeInMillis = jsonObject.getJSONObject(i).getLong(TAG_CREATION_DATE)
                    var creationTime = calendar.time
                    question.creationDate = SimpleDateFormat("dd-MM-yyyy").format(creationTime)
                }
                if(jsonObject.getJSONObject(i).has(TAG_LAST_ACTIVITY_DATE)){
                calendar.timeInMillis= jsonObject.getJSONObject(i).getLong(TAG_LAST_ACTIVITY_DATE)
                var lastActiveDate = calendar.time
                question.lastActiveDate = SimpleDateFormat("dd-MM-yyyy").format(lastActiveDate)
                }

                if(jsonObject.getJSONObject(i).has(TAG_QUESTION_ID))
                    question.questionId     = jsonObject.getJSONObject(i).getString(TAG_QUESTION_ID)

                if(jsonObject.getJSONObject(i).has(TAG_CONTENT_LICENSE))
                    question.contentLicense = jsonObject.getJSONObject(i).getString(TAG_CONTENT_LICENSE)

                if(jsonObject.getJSONObject(i).has(TAG_TAGS)) {
                    var tags = jsonObject.getJSONObject(i).getJSONArray(TAG_TAGS)
                    var tagsArrayList = arrayListOf<String>()
                    for (i in 0 until tags.length()) {
                        tagsArrayList.add(tags.getString(i))
                    }
                    question.tags = tagsArrayList
                }

                if(jsonObject.getJSONObject(i).has(TAG_OWNER)) {
                    var ownerJsonObject = jsonObject.getJSONObject(i).getJSONObject(TAG_OWNER)

                    var owner = QuestionOwner()
                    owner.reputation = ownerJsonObject.getInt(TAG_REPUTATION)
                    owner.userId = ownerJsonObject.getString(TAG_USER_ID)
                    owner.userType = ownerJsonObject.getString(TAG_USER_TYPE)
                    owner.profileImageUrl = ownerJsonObject.getString(TAG_PROFILE_IMAGE)
                    owner.displayName = ownerJsonObject.getString(TAG_DISPLAY_NAME)
                    owner.link = ownerJsonObject.getString(TAG_LINK)
                    question.owner = owner
                }
                listNewsObject.add(question)
            }
            _questionList.value?.clear()
            _questionList.value = listNewsObject
        }


        }, {
                error ->
            Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show()
            Log.d("ErrorVolley",error.toString())
        })
        volley.add(requestData)


    }


}