package com.ominext.githubrepositoryfinder.main

import com.ominext.githubrepositoryfinder.model.Item
import io.realm.Realm
import io.realm.RealmConfiguration

class MainModel(var iView : MainContract.View):MainContract.Model {
    override fun initRealm(){
        Realm.init(iView.getContext())
    }
    override fun retrieveDataFromRealmDB(): List<Item> {
        val config2 = RealmConfiguration.Builder()
            .name("default2.realm")
            .schemaVersion(3)
            .deleteRealmIfMigrationNeeded()
            .build()
        val realm = Realm.getInstance(config2)
        val result = realm.where(Item::class.java).findAll()
        val resultList = ArrayList<Item>()
        result.forEach {
            resultList.add(it)
        }
        return resultList
    }
}