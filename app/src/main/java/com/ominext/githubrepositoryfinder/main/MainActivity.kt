package com.ominext.githubrepositoryfinder.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ominext.githubrepositoryfinder.R
import com.ominext.githubrepositoryfinder.detail.DetailActivity
import com.ominext.githubrepositoryfinder.model.Item
import com.ominext.githubrepositoryfinder.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(),
    MainContract.View {
    lateinit var presenter:MainContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpPresenter()
        initRcv()
        presenter.initRealm()
    }
    private fun initRcv(){
        rcvRepository.layoutManager = LinearLayoutManager(this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        presenter.getData()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.search){
            startSearchActivity()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun startSearchActivity(){
        val i = Intent(this@MainActivity, SearchActivity::class.java)
        startActivity(i)
    }

    override fun setUpPresenter() {
        presenter = MainPresenterImpl(this)
    }

    override fun showError(errorMsg: String) {
        status.visibility = View.VISIBLE
        status.text = errorMsg
    }

    override fun getContext(): Context {
        return this
    }

    override fun startDetailActivity(item: Item) {
        val i = Intent(this@MainActivity, DetailActivity::class.java)
        i.putExtra("repoName",item.fullName)
        i.putExtra("ownerName",item.owner?.login)
        i.putExtra("description",item.description)
        i.putExtra("repoUrl",item.url)
        i.putExtra("language",item.language)
        startActivity(i)
    }

    override fun setUpData(itemList: List<Item>) {
        status.visibility = View.GONE
        rcvRepository.adapter = MainAdapter(itemList,this)
    }

    inner class MainAdapter(
        var repositoryList: List<Item>,
        context: Context
    ) :
        RecyclerView.Adapter<MainAdapter.RepositoryHolder>() {
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
                startDetailActivity(repositoryList[position])
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
