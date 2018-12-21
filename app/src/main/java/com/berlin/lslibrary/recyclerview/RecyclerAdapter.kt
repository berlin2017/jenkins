package com.berlin.lslibrary.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.berlin.lslibrary.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private var context: Context
    private var list: List<String>

    constructor(context: Context, list: List<String>) {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview, p0, false))
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.text.text = "this is text " + p1
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var text: TextView

        constructor(itemView: View) : super(itemView) {
            text = itemView.findViewById(R.id.text)
        }
    }
}