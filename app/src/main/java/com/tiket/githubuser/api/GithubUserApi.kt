package com.tiket.githubuser.api

import android.net.Uri
import com.tiket.githubuser.models.GithubUserApiModel
import com.tiket.githubuser.models.GithubUserModel
import com.tiket.githubuser.utils.toObject
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
class GithubUserApi {

    companion object {
        private const val baseUrl = "https://api.github.com/search/users"
    }

    private val client: HttpClient by lazy {
        HttpClient()
    }

    private var lastQuery = ""
    private val perPage = 50
    var page = 1

    private var _query = ""
    var query: String
    get() = _query
    set(value) {
        if (_query != value)
            page = 1
        _query = value
    }

    private val _url: String
    get() {
        if (query == lastQuery)
            page++
        else
            page = 1
        if (page < 1)
            page = 1
        lastQuery = query

        return Uri.parse(baseUrl).buildUpon()
            .appendQueryParameter("q", query)
            .appendQueryParameter("order", "desc")
            .appendQueryParameter("per_page", "$perPage")
            .appendQueryParameter("page", "$page")
            .build().toString()
    }

    suspend fun fetchUser(): Pair<Int, List<GithubUserModel>> {
        val response = client.get<HttpResponse>{ url(_url) }
        val status = response.status.value
        val content = response.readText()
        var users = listOf<GithubUserModel>()

        when (status) {
            200 -> {
                val m = content.toObject<GithubUserApiModel>()
                users = m.items
            }
            else -> page--
        }

        return Pair(status, users)
    }

}
