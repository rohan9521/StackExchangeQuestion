package com.example.stackexchangequestion.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackexchangequestion.Model.FilterOptions
import com.example.stackexchangequestion.Model.Question
import com.example.stackexchangequestion.R
import kotlinx.android.synthetic.main.layout_filter_options.view.*

class FilterOptionAdapter(
    private val context: Context,
    private val clickListener: View.OnClickListener,
    private val optionsList:ArrayList<FilterOptions>
): RecyclerView.Adapter<FilterOptionAdapter.FilterOptionViewHolder>() {

    private val inflator: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterOptionViewHolder {
        val view = inflator.inflate(R.layout.layout_filter_options, parent, false)
        return FilterOptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterOptionViewHolder, position: Int) {
        holder.populate(optionsList[position], clickListener)
    }


    override fun getItemCount(): Int = optionsList.size

    inner class FilterOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populate(options: FilterOptions, clickListener: View.OnClickListener) {
            itemView.tag = options
                itemView.lfo_tv_option.text = options.label
            itemView.setOnClickListener(clickListener)
        }

    }
}

