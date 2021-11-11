package com.example.stackexchangequestion.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newseveryday.QuestionViewModel
import com.example.stackexchangequestion.Adapter.QuestionAdapter
import com.example.stackexchangequestion.Model.FilterOptions
import com.example.stackexchangequestion.Model.Question
import com.example.stackexchangequestion.R
import com.example.stackexchangequestion.component.FilterDialog
import kotlinx.android.synthetic.main.activity_main.*

class QuestionActivity : AppCompatActivity() {
    private lateinit var selectedFilterOptions:FilterOptions
    private var filterList:ArrayList<FilterOptions> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        qa_rv_question_list.layoutManager = LinearLayoutManager(this)
        var questionList = mutableListOf<Question>()
        val adapter = QuestionAdapter(this, arrayListOf<Question>(),View.OnClickListener {  })
        qa_rv_question_list.adapter = adapter

        viewModel.questionList.observe(this, Observer {
                list->
            run {
                Log.d("list",list.toString())
                questionList = list
                adapter.questionList = list
                adapter.notifyDataSetChanged()
                var filterTag:MutableSet<String> = mutableSetOf()
                questionList.forEach { question ->
                    for(tag in question.tags){
                        filterTag.add(tag)
                    }
                }


                var id:Int = 0

                for(tag in filterTag){

                    filterList.add(FilterOptions(id++,tag))
                }

                aq_img_filter.setOnClickListener(View.OnClickListener {
                    val filterDialog = FilterDialog(this,"Filter by Tags")
                        filterDialog.showDialog(filterList,View.OnClickListener {
                        selectedFilterOptions = it.tag as FilterOptions
                        var list:MutableList<Question> = mutableListOf()
                         questionList.forEach{ question->
                            for(tag in question.tags){
                                if(tag== selectedFilterOptions.label){
                                    list.add(question)
                                }
                            }
                        }

                        adapter.questionList = list

                        adapter.notifyDataSetChanged()
                            filterDialog.dismiss()

                    })
                })
            }
        })

        viewModel.fetchData(this)

        aq_edt_search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                aq_edt_search.removeTextChangedListener(this)

                var list = questionList.filter { question->(question.title.contains(s.toString(),true) || question.owner?.displayName?.contains(s.toString(),true) ?:false )} as MutableList

                adapter.questionList = list

                adapter.notifyDataSetChanged()

                aq_edt_search.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })



    }
}