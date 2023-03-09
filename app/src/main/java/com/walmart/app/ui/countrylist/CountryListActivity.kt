package com.walmart.app.ui.countrylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.walmart.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_country_list)
    }
}
