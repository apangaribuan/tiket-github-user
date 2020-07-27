package com.tiket.githubuser

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiket.githubuser.api.GithubUserApi
import com.tiket.githubuser.models.GithubUserModel
import com.tiket.githubuser.models.GithubUserViewHolder
import com.tiket.githubuser.utils.OnLoadMoreListener
import com.tiket.githubuser.utils.RecyclerViewItemAdapter
import com.tiket.githubuser.utils.RecyclerViewLoadMoreScroll
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
class MainActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var itemsCells: ArrayList<GithubUserModel?>
    private lateinit var rvItemAdapter: RecyclerViewItemAdapter<GithubUserModel>
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var loadDialog: Dialog

    private val api = GithubUserApi()

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(R.layout.indeterminate_progress)
        loadDialog = dialogBuilder.create()

        btnSearch.setOnClickListener { search() }

        itemsCells = ArrayList()
        rvItemAdapter = RecyclerViewItemAdapter(itemsCells, R.layout.item_github_user, R.layout.progress_loading) {
            GithubUserViewHolder(it)
        }
        rvItemAdapter.notifyDataSetChanged()
        rvUser.adapter = rvItemAdapter

        layoutManager = LinearLayoutManager(this)
        rvUser.layoutManager = layoutManager
        rvUser.setHasFixedSize(true)

        scrollListener = RecyclerViewLoadMoreScroll(layoutManager as LinearLayoutManager, 5)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener{
            override fun onLoadMore() {
                loadMoreUser()
            }
        })
        rvUser.addOnScrollListener(scrollListener)
    }


    private fun loadMoreUser() {
        rvItemAdapter.addLoadingView()
        query {
            rvItemAdapter.removeLoadingView()
            scrollListener.setLoaded()
        }
    }


    private fun search() {
        val userName = etUser.text.trim().toString()
        if (userName.isEmpty())
            return

        this.currentFocus?.let {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            manager?.hideSoftInputFromWindow(it.windowToken, 0)
        }

        if (api.query != userName) {
            rvItemAdapter.clear()
        }

        loadDialog.show()
        api.query = userName
        query {
            loadDialog.hide()
        }
    }


    private fun query(onFinished: () -> Unit) {
        launch(Dispatchers.Main) {
            try {
                val (status, users) = withContext(Dispatchers.IO) { api.fetchUser() }
                if (status == 200) {
                    rvItemAdapter.addData(users)
                }
            } catch (ex: Exception) {
                api.page--
                Log.e(TAG, "error: $ex")
            } finally {
                onFinished()
            }
        }
    }
}
