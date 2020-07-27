package com.tiket.githubuser.models

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tiket.githubuser.utils.RVBinder


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
abstract class RVHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), RVBinder<T> {}
