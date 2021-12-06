package com.code.fireworksdk

import android.app.Application
import android.provider.Settings
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.loopnow.fireworklibrary.FwSDK
import com.loopnow.fireworklibrary.SdkStatus
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.MessageDigest.getInstance

class App:Application() {
    private val clientId = "3cba6bd2cd03b5227896ac38bc33b46211fb971605ef33b73999bfd0d096fe8d"
    companion object {
        val fwInitialized = MutableLiveData(false)
    }
    private val hashedUserId by lazy {
        getHash(Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        ))
    }
    private fun getHash(text: String) : String {
        val digest: MessageDigest = getInstance("SHA-256")
        val hash: ByteArray = digest.digest(text.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(hash,Base64.NO_WRAP)
    }
    /**
     * Initialize firework SDK in onCreate method.
     * @param FwSdkStatusListener receive the SDK status update with the callback
     * FwSDK.SdkStatusListener
     *
     */
    override fun onCreate() {
        super.onCreate()
        FwSDK.initialize(this, clientId, hashedUserId, object : FwSDK.SdkStatusListener {
            override fun currentStatus(status: SdkStatus, extra: String) {
                Log.v("SdkStatusLog", "$status   -> $extra " )
                when(status) {
                    SdkStatus.Initialized -> {
                        fwInitialized.value  = true
                    }
                }
            }
        })

    }




}