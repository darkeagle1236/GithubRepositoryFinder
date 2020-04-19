package com.ominext.githubrepositoryfinder.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ominext.githubrepositoryfinder.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if(intent!=null){
            tvRepoName.text = intent.getStringExtra("repoName")
            tvDescription.text ="Description: "+ intent.getStringExtra("description")
            tvLanguage.text ="Language: "+ intent.getStringExtra("language")
            tvOwnerName.text ="Owner: " + intent.getStringExtra("ownerName")
            tvURL.text ="URL: " + intent.getStringExtra("repoUrl")
        }
    }
}
