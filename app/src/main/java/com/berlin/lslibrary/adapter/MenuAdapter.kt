package com.berlin.lslibrary.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.berlin.lslibrary.R
import com.berlin.lslibrary.model.Menu

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private var context: Context
    private var list: List<Menu>

    constructor(context: Context, list: List<Menu>) {
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
        p0.text.text = list.get(p1).name
        p0.itemView.setOnClickListener(View.OnClickListener(){
            var intent = Intent(context, Class.forName(list.get(p1).`class`))
            context.startActivity(intent)
        })
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var text: TextView

        constructor(itemView: View) : super(itemView) {
            text = itemView.findViewById(R.id.text)
        }
    }
}