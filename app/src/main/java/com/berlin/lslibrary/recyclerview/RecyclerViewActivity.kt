package com.berlin.lslibrary.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.berlin.lslibrary.R
import com.berlin.lsrefresh.JDRefreshHeader
import com.berlin.lsrefresh.LSRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlin.collections.ArrayList

class RecyclerViewActivity : AppCompatActivity(), LSRefreshLayout.OnRefreshListener {

    private lateinit var adapter: RecyclerAdapterNew
    private var list = ArrayList<String>()
    private lateinit var headerView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = RecyclerAdapterNew(list)
        adapter.isUpFetchEnable = true
        adapter.setEnableLoadMore(true)
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            Toast.makeText(this@RecyclerViewActivity, "onItemClick" + position, Toast.LENGTH_SHORT).show()
        }
        adapter.openLoadAnimation()
        headerView = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, recyclerView.parent as ViewGroup,false )
        adapter.addHeaderView(headerView)

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            adapter.loadMoreComplete()
        }, recyclerView)

        recyclerView.adapter = adapter
        refreshLayout.autoRefresh()
        refreshLayout.refreshListener = this
        refreshLayout.setRefreshHeader(JDRefreshHeader(this))

        setData()
    }

    fun setData() {
        for (i in 0..20) {
            list.add("This is String " + i)
        }
        adapter.notifyDataSetChanged()

    }

    override fun onRefresh() {
        Toast.makeText(this, "刷新", Toast.LENGTH_LONG).show()
        refreshLayout.postDelayed(Runnable {
            refreshLayout.refreshComplete()
        }, 2000)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        } else if (item?.itemId == R.id.recy_switch) {
            if (recyclerView.layoutManager is GridLayoutManager) {
                recyclerView.layoutManager = LinearLayoutManager(this)
            } else {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recyclerview, menu)
        return super.onCreateOptionsMenu(menu)
    }
}