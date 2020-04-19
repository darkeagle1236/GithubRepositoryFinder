package com.ominext.githubrepositoryfinder.main

import com.ominext.githubrepositoryfinder.model.Item

class MainPresenterImpl(var iView:MainContract.View):MainContract.Presenter {
    var iModel = MainModel(iView)
    override fun getData() {
        var dataList = iModel.retrieveDataFromRealmDB()
        if (dataList.isNotEmpty()){
            iView.setUpData(dataList)
        }
        else{
            iView.showError("Viewed repository display here")
        }
    }


    override fun initRealm() {
        iModel.initRealm()
    }
}