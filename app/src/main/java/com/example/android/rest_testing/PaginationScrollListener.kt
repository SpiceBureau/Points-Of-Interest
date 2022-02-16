package com.example.android.rest_testing

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(layout: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    var layoutManager: LinearLayoutManager
    private var PAGE_SIZE = 10

    init {
        layoutManager = layout
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var visibleItemCount = layoutManager.childCount
        var totalItemCount = layoutManager.itemCount
        var firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

        if(!isLoading() && !isLastPage()){
            if((visibleItemCount + firstVisiblePosition) >= totalItemCount && firstVisiblePosition >= 0 && totalItemCount >= PAGE_SIZE){
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

}