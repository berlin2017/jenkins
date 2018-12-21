package com.berlin.lslibrary.recyclerview

import android.view.View
import android.widget.TextView
import com.berlin.lslibrary.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RecyclerAdapterNew(list: ArrayList<String>) :BaseQuickAdapter<String,RecyclerAdapterNew.ViewHolder>(R.layout.item_recyclerview,list){

    override fun convert(helper: ViewHolder?, item: String?) {
        helper?.text?.text = item
    }

    class ViewHolder : BaseViewHolder {
        var text: TextView

        constructor(itemView: View) : super(itemView) {
            text = itemView.findViewById(R.id.text)
        }
    }
}