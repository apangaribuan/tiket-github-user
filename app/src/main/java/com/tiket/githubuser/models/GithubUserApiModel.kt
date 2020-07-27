package com.tiket.githubuser.models

import com.google.gson.annotations.SerializedName
import com.tiket.githubuser.utils.JSONConvertable


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
data class GithubUserApiModel(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<GithubUserModel>
) : JSONConvertable

data class GithubUserModel(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
