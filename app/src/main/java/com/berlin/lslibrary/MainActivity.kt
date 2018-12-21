package com.berlin.lslibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.Toast
import com.berlin.lslibrary.adapter.MenuAdapter
import com.berlin.lslibrary.model.Menu
import com.berlin.lslibrary.utils.Utils
import com.berlin.lsrefresh.LSRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), LSRefreshLayout.OnRefreshListener {

    private lateinit var adapter: MenuAdapter
    private var list = ArrayList<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(this, list)
        recyclerView.adapter = adapter
        getData()
    //
        refreshLayout.isEnabled = false
        refreshLayout.refreshListener = this
    }

    fun getData() {
        var result = Utils.readAssets(this, "menu.json")
        if (!TextUtils.isEmpty(result)) {
            var gson = Gson()
            var type = object : TypeToken<List<Menu>>() {}.type
            var datas = gson.fromJson<List<Menu>>(result, type)
            if (datas != null && datas.size > 0) {
                list.addAll(datas)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onRefresh() {
        Toast.makeText(this, "刷新", Toast.LENGTH_LONG).show()
        refreshLayout.postDelayed(Runnable {
            refreshLayout.refreshComplete()
        }, 2000)
    }
}
