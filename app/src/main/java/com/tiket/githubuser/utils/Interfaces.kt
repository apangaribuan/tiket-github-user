package com.tiket.githubuser.utils

import com.google.gson.Gson


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
interface OnLoadMoreListener {
    fun onLoadMore()
}

interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)
}

interface RVBinder<T> {
    fun onBind(model: T)
}
