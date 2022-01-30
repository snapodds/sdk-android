package com.snapscreen.example

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.snapscreen.example.databinding.ActivityMainBinding
import com.snapscreen.mobile.Snapscreen
import com.snapscreen.mobile.model.snap.SportEventSnapResultEntry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.snapButton.setOnClickListener {
            startActivityForResult(Intent(this, SnapActivity::class.java), 123)
        }
        binding.oddsButton.setOnClickListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            (data?.getSerializableExtra("sportEvent") as? SportEventSnapResultEntry)?.let {
                AlertDialog.Builder(this, 0)
                    .setTitle("Snapped Sport Event")
                    .setMessage("Successfully snapped sport event with id ${it.sportEvent?.eventId ?: -1} on ${it.tvChannel?.name ?: "Unknown channel"}")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(true)
                    .create()
                    .show()
            }
        }
    }
}