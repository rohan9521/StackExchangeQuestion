package com.example.stackexchangequestion.component

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchangequestion.Adapter.FilterOptionAdapter
import com.example.stackexchangequestion.Model.FilterOptions
import com.example.stackexchangequestion.R
import kotlinx.android.synthetic.main.layout_filter_dialog.*

class FilterDialog(
    private val activity: Activity,
    private val title:String
) {
    private val dialog:Dialog
    init {
        dialog = Dialog(activity, R.style.DialogTheme).apply {
            setContentView(R.layout.layout_filter_dialog)
            window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            lom_img_dismiss.setOnClickListener{
                this.dismiss()
            }
            lom_tv_title.text = title
        }
    }

    fun showDialog(optionsList: ArrayList<FilterOptions>, clickListener: View.OnClickListener) {
        rvSetup(optionsList,dialog.lom_rv_options_list,clickListener)
        dialog.show()
    }

    private fun rvSetup(optionsList:ArrayList<FilterOptions>, recyclerView: RecyclerView, clickListener: View.OnClickListener){
        val optionsMenuAdapter = FilterOptionAdapter(activity,clickListener,optionsList)
        recyclerView.adapter = optionsMenuAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    val isShowing: Boolean?
        get() = dialog.isShowing

    fun dismiss() {
        dialog.dismiss()
    }
}