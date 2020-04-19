package com.ominext.githubrepositoryfinder.main

import android.content.Context
import com.ominext.githubrepositoryfinder.model.Item

interface MainContract{
    interface View{
        fun setUpPresenter()
        fun showError(errorMsg:String)
        fun getContext():Context
        fun startDetailActivity(item: Item)
        fun setUpData(itemList:List<Item>)
    }
    interface Presenter{
        fun getData()
        fun initRealm()
    }
    interface Model{
        fun initRealm()
        fun retrieveDataFromRealmDB():List<Item>
    }
}