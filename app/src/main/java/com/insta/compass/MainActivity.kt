package com.insta.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var manager: SensorManager? = null
    private var currentDegree: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getToolbar()

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(
            this,
            manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )

    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree: Int = event?.values?.get(0)?.toInt()!!
        tvDegree.text = degree.toString()
        val rotationAnim = RotateAnimation(
            currentDegree.toFloat(), (-degree).toFloat(), Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        currentDegree = -degree
        imDinamic.startAnimation(rotationAnim)

        /*  view.animate().apply{
              rotationBy(90f)
              duration = 1000L
              start()
          }*/
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun getToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.app_name)
        supportActionBar?.subtitle = "North, South, West, East"
        supportActionBar?.setDisplayUseLogoEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reset -> {
                finish()
                startActivity(intent)
            }
            R.id.about -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("About")
                builder.setMessage(R.string.compass)
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("OK") { _, _ ->

                }
                builder.show()
            }
            R.id.exit ->
                finish()
        }
        return (super.onOptionsItemSelected(item))
    }

}
