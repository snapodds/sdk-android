package com.snapscreen.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snapscreen.example.databinding.ActivitySnapBinding
import com.snapscreen.mobile.model.snap.SportEventSnapResultEntry
import com.snapscreen.mobile.snap.SnapConfiguration
import com.snapscreen.mobile.snap.SnapFragment
import com.snapscreen.mobile.snap.SnapResultListener

class SnapActivity: AppCompatActivity(), SnapResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySnapBinding.inflate(layoutInflater)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, SnapFragment.newInstance(SnapConfiguration(), this), "SnapFragmentTag")
        ft.commitAllowingStateLoss()
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun snapFragmentDidSnapSportEvent(
        fragment: SnapFragment,
        sportEvent: SportEventSnapResultEntry
    ): Boolean {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra("sportEvent", sportEvent) })
        finish()
        return true
    }
}