package com.ominext.githubrepositoryfinder.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ominext.githubrepositoryfinder.R
import com.ominext.githubrepositoryfinder.detail.DetailActivity
import com.ominext.githubrepositoryfinder.model.Item
import com.ominext.githubrepositoryfinder.search.SearchContract.Presenter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlinx.android.synthetic.main.toolbar.*


class SearchActivity : AppCompatActivity(), SearchContract.View {
    lateinit var presenter: Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        setUpPresenter()
        initRcv()
    }
    private fun initRcv(){
        rcvSearchResult.layoutManager = LinearLayoutManager(this)
    }
    override fun setUpData(repoList:List<Item>){
        tvError.visibility = View.GONE
        rcvSearchResult.adapter =
            SearchAdapter(
                repoList!!,
                this
            )
    }

    override fun startDetailActivity(item: Item) {
        val i = Intent(this@SearchActivity, DetailActivity::class.java)
        i.putExtra("repoName",item.fullName)
        i.putExtra("ownerName",item.owner?.login)
        i.putExtra("description",item.description)
        i.putExtra("repoUrl",item.url)
        i.putExtra("language",item.language)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView
        searchView.onActionViewExpanded()
        presenter.getResultsBasedOnQuery(searchView)
        return super.onCreateOptionsMenu(menu)
    }
    override fun setUpPresenter() {
        presenter = SearchPresenterImpl(this)
    }

    override fun showProgress() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarLayout.visibility = View.GONE
    }

    override fun showError(errorMsg: String) {
        tvError.visibility = View.VISIBLE
        tvError.text = errorMsg
    }
    inner class SearchAdapter(
        private var repositoryList: List<Item>,
        context: Context
    ) :
        RecyclerView.Adapter<SearchAdapter.RepositoryHolder>() {
        var context: Context = context
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RepositoryHolder {
            val v: View =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_repository, parent, false)
            return RepositoryHolder(v)
        }

        override fun onBindViewHolder(
            holder: RepositoryHolder,
            position: Int
        ) {
            holder.tvRepoName.text = repositoryList[position].fullName
            holder.tvRepoLanguage.text = repositoryList[position].language
            holder.tvRepoOwnerName.text = repositoryList[position].owner?.login
            holder.view.setOnClickListener(View.OnClickListener {
                presenter.onItemClickListener(repositoryList[position])
            })
        }

        override fun getItemCount(): Int {
            return repositoryList.size
        }

        inner class RepositoryHolder(v: View) : RecyclerView.ViewHolder(v) {
            var tvRepoName: TextView = v.tvRepoName
            var tvRepoOwnerName: TextView = v.tvRepoOwnerName
            var tvRepoLanguage: TextView = v.tvRepoLanguage
            var view = v
        }

    }
}
