package com.tiket.githubuser.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
class RecyclerViewLoadMoreScroll(
    private val layoutManager: LinearLayoutManager,
    private val visibleThreshold: Int
) : RecyclerView.OnScrollListener() {
    private lateinit var loadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        loadMoreListener = listener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0)
            return

        totalItemCount = layoutManager.itemCount
        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        Log.e("scroll", "totalItemCount: $totalItemCount, <= : ${lastVisibleItem + visibleThreshold}, $lastVisibleItem  $visibleThreshold")
        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            loadMoreListener.onLoadMore()
            isLoading = true
        }
    }
}
