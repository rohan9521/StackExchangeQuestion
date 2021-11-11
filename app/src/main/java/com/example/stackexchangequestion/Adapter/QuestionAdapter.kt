package com.example.stackexchangequestion.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackexchangequestion.Model.Question
import com.example.stackexchangequestion.R


class QuestionAdapter(
    private val context: Context,
    var questionList: MutableList<Question>,
    private val clickListener: View.OnClickListener
)
    : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private val inflator:LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = inflator.inflate(R.layout.layout_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.populate(questionList[position], clickListener)
    }


    override fun getItemCount(): Int = questionList.size

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populate(question: Question, clickListener: View.OnClickListener) {
            var title = itemView.findViewById<TextView>(R.id.lq_tv_question_title)
            var datePosted = itemView.findViewById<TextView>(R.id.lq_tv_date_posted)
            var ownerName = itemView.findViewById<TextView>(R.id.lq_tv_owner_name)
            var ownerImage = itemView.findViewById<ImageView>(R.id.lq_img_owner_image)

            Glide.with(context)
                .load(question.owner?.profileImageUrl)
                .into(ownerImage)

            title.text =  question.title

            ownerName.text = question.owner?.displayName ?: ""

            datePosted.text = question.creationDate

            itemView.setOnClickListener(View.OnClickListener {

                val customTabsIntent = CustomTabsIntent.Builder().build();
                customTabsIntent.launchUrl(context, Uri.parse(question.link));
            })
        }

    }

}