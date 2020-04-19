package com.ominext.githubrepositoryfinder.search

import androidx.appcompat.widget.SearchView
import com.ominext.githubrepositoryfinder.model.Item
import com.ominext.githubrepositoryfinder.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SearchPresenterImpl(var iView: SearchContract.View) :
    SearchContract.Presenter {
    private val iModel:SearchContract.Model = SearchModel()
    override fun getResultsBasedOnQuery(searchView: SearchView) {
        getObservableQuery(searchView).filter { s -> s != "" }
            .switchMap { s ->
                iView.showProgress()
                iModel.doServerSearchCall(s)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }

    override fun onItemClickListener(item: Item) {
        println(item.description)
        iModel.saveToRealmDB(item)
        iView.startDetailActivity(item)
    }

    private fun getObservableQuery(searchView: SearchView): Observable<String> {
        val publishSubject: PublishSubject<String> = PublishSubject.create()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                publishSubject.onNext(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return publishSubject
    }

    private val observer: DisposableObserver<SearchResponse>
        get() = object : DisposableObserver<SearchResponse>() {
            override fun onComplete() {

            }

            override fun onNext(t: SearchResponse) {
                if (t.items.isEmpty()){
                    iView.hideProgress()
                    iView.showError("Not found")
                    return
                }
                else{
                    iView.setUpData(t.items)
                    iView.hideProgress()
                    return
                }
            }

            override fun onError(e: Throwable) {
                iView.showError(e.toString())
                return
            }
        }
}