package com.ominext.githubrepositoryfinder.model


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class SearchResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean,
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("total_count")
    var totalCount: Int
)