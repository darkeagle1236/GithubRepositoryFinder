package com.ominext.githubrepositoryfinder.search

import android.util.Log
import com.ominext.githubrepositoryfinder.model.Item
import com.ominext.githubrepositoryfinder.model.SearchResponse
import com.ominext.githubrepositoryfinder.network.API
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration


class SearchModel :SearchContract.Model {
    override fun doServerSearchCall(query: String): Observable<SearchResponse> {
        return API.service.search(query)
    }

    override fun saveToRealmDB(item: Item) {
        val config2 = RealmConfiguration.Builder()
            .name("default2.realm")
            .schemaVersion(3)
            .deleteRealmIfMigrationNeeded()
            .build()
        val realm = Realm.getInstance(config2)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(item)
        realm.commitTransaction()
    }


}