package com.ominext.githubrepositoryfinder.search

import androidx.appcompat.widget.SearchView
import com.ominext.githubrepositoryfinder.model.Item
import io.reactivex.Observable
import com.ominext.githubrepositoryfinder.model.SearchResponse

interface SearchContract {
    interface View{
        fun setUpPresenter()
        fun showProgress()
        fun hideProgress()
        fun showError(errorMsg:String)
        fun setUpData(repoItems: List<Item>)
        fun startDetailActivity(item: Item)
    }
    interface Presenter{
        fun getResultsBasedOnQuery(searchView: SearchView)
        fun onItemClickListener(item: Item)
    }
    interface Model{
        fun doServerSearchCall(query:String): Observable<SearchResponse>
        fun saveToRealmDB(item: Item)
    }
}