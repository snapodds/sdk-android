package com.snapscreen.example

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.snapscreen.example.databinding.ActivityMainBinding
import com.snapscreen.mobile.Snapscreen
import com.snapscreen.mobile.model.snap.SportEventSnapResultEntry
import com.snapscreen.mobile.snap.SnapActivity
import com.snapscreen.mobile.snap.SnapActivityResultListener
import com.snapscreen.mobile.snap.SnapConfiguration
import com.snapscreen.mobile.snap.SnapFragment

class MainActivity : AppCompatActivity(), SnapActivityResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.snapSportsMediaButton.setOnClickListener {
            val intent = SnapActivity.intentForSportsMedia(this, SnapConfiguration());
            startActivity(intent)
        }
        binding.snapSportsOperatorButton.setOnClickListener {
            val intent = SnapActivity.intentForSportsOperator(this, SnapConfiguration(), this);
            startActivity(intent)
        }
        binding.loadOddsButton.setOnClickListener {
            loadOdds()
        }
    }

    private fun loadOdds() {
        Snapscreen.instance?.getOddsForSportEvent(494) { result ->
            AlertDialog.Builder(this, 0)
                .setTitle(if (result != null) "Fetched odds" else "No odds found")
                .setMessage(if (result != null) "Successfully fetched odds from ${result.sportsBooks?.count() ?: 0} providers for sport event" else null)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(true)
                .create()
                .show()
        }
    }

    override fun snapActivityDidSnapSportEvent(
        activity: SnapActivity,
        fragment: SnapFragment,
        sportEvent: SportEventSnapResultEntry
    ) {
        activity.finish()

        AlertDialog.Builder(this, 0)
            .setTitle("Snapped Sport Event")
            .setMessage("Snapped Sport Event with ID ${sportEvent.sportEvent?.eventId} on channel ${sportEvent.tvChannel?.name}")
            .setNeutralButton("Show odds") { dialog, which ->
                val intent = Intent(this, OddsResultsActivity::class.java).apply {
                    putExtra("sportEvent", sportEvent.sportEvent!!)
                    putExtra("tvChannel", sportEvent.tvChannel)
                }
                startActivity(intent)
            }
            .setPositiveButton("OK") { _, _ ->
            }
            .setCancelable(false)
            .create()
            .show()
    }

}