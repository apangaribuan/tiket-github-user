package com.tiket.githubuser.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.tiket.githubuser.models.RVHolder


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
class RecyclerViewItemAdapter<T>(
    private var itemsCells: ArrayList<T?>,
    @LayoutRes private val itemLayout: Int,
    @LayoutRes private val loadingLayout: Int,
    private val viewHolder: (view: View) -> RVHolder<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var loadingPosition = -1


    fun clear() {
        itemsCells.clear()
        notifyDataSetChanged()
    }

    fun addData(dataViews: List<T>) {
        removeLoadingView()
        itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        itemsCells.add(null)
        loadingPosition = itemsCells.size - 1
        notifyItemInserted(loadingPosition)
    }

    fun removeLoadingView() {
        if (loadingPosition > -1) {
            itemsCells.removeAt(loadingPosition)
            notifyItemRemoved(loadingPosition)
            loadingPosition = -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == Const.VIEW_TYPE_ITEM) {
            val view = inflater.inflate(itemLayout, parent, false)
            viewHolder(view)
        } else {
            val view = inflater.inflate(loadingLayout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsCells[position] == null) {
            Const.VIEW_TYPE_LOADING
        } else {
            Const.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Const.VIEW_TYPE_ITEM) {
            itemsCells[position]?.let {
                (holder as RVHolder<T>).onBind(it)
            }
        }
    }
}
