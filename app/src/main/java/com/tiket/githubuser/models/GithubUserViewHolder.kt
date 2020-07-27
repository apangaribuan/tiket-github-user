package com.tiket.githubuser.models

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tiket.githubuser.R
import kotlinx.android.synthetic.main.item_github_user.view.*


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
class GithubUserViewHolder(itemView: View) : RVHolder<GithubUserModel>(itemView) {

    private var tvUserName = itemView.tvUserName
    private var ivAvatar = itemView.ivAvatar

    override fun onBind(model: GithubUserModel) {
        tvUserName.text = model.name
        Glide.with(ivAvatar)
            .load(model.avatarUrl)
            .placeholder(R.drawable.ic_user)
            .apply(RequestOptions().circleCrop())
            .into(ivAvatar)
    }

}
