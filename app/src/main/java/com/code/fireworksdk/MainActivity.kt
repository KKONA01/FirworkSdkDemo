package com.code.fireworksdk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.code.fireworksdk.databinding.ActivityMainBinding
import com.loopnow.fireworklibrary.AdLabelType
import com.loopnow.fireworklibrary.FwSDK
import com.loopnow.fireworklibrary.VideoFeedProperties
import com.loopnow.fireworklibrary.VideoPlayerProperties
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
       VideoFeedProperties.displayAdLabel  = true
        VideoFeedProperties.setAdLabel(AdLabelType.AD)
        VideoPlayerProperties.enableOneTouchMute = true
        VideoPlayerProperties.autoPlayOnComplete = false
        VideoPlayerProperties.enablePlayPauseControl = false
        VideoPlayerProperties.launchPlayerWithMute = true
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

            override fun ctaClicked(label: String, actionUrl: String?): Boolean {
                return false
            }
        }
    }

}