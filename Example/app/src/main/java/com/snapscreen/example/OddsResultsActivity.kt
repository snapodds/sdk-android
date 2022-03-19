package com.snapscreen.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snapscreen.example.databinding.ActivitySnapBinding
import com.snapscreen.mobile.model.snap.SportEvent
import com.snapscreen.mobile.model.snap.TvChannel
import com.snapscreen.mobile.odds.OddsResultsFragment

class OddsResultsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sportEvent = intent.getSerializableExtra("sportEvent") as SportEvent
        val tvChannel = intent.getSerializableExtra("tvChannel") as? TvChannel

        val binding = ActivitySnapBinding.inflate(layoutInflater)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, OddsResultsFragment.newInstance(
            sportEvent,
            tvChannel
        ), "OddsResultsFragmentTag")
        ft.commitAllowingStateLoss()
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}