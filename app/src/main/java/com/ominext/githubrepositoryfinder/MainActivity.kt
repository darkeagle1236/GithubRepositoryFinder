package com.ominext.githubrepositoryfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ominext.githubrepositoryfinder.model.SearchResponse
import com.ominext.githubrepositoryfinder.network.GetDataService
import com.ominext.sakuracampus.network.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search()
    }
    fun search(){
        val service = RetrofitClientInstance.client.create(GetDataService::class.java)
        val call: Call<SearchResponse> = service.search("NutritionTracker")
        call.enqueue(object : Callback<SearchResponse>{
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                status.text = "Code sai lòi loz r nha e iu"
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if(response.isSuccessful){
                    status.text = "Thành công r nha e iu"
                }
            }

        })
    }
}
