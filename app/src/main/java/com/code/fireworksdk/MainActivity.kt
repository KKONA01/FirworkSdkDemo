package com.code.fireworksdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.code.fireworksdk.databinding.ActivityMainBinding
import com.loopnow.fireworklibrary.FwSDK
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.grid.setPlaylist("","vJ0Yno")
        binding.grid.start()



        FwSDK.addVideoEventListener(object : FwSDK.VideoEventListener {
            override fun event(event: String, jsonObject: JSONObject) {
                // placyback events and json payload
                // refer to documentation for event details and payload
                // https://docs.firework.tv/android-sdks/android
                // tab playback events
                Log.d("TAG", "event: $event ${jsonObject.toString()}")
            }
        })

        // If you were to handle the CTA click,
        // Implement CtaClickHandler

        FwSDK.ctaClickHandler = object : FwSDK.CtaClickHandler {
            /**
             * return true if you handled, false if you want SDK to handle it.
             */
            override fun ctaClicked(label: String, actionUrl: String?): Boolean {
                return false
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}