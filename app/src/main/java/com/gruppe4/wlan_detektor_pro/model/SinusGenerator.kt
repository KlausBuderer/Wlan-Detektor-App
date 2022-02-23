package com.gruppe4.wlan_detektor_pro.model

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.NetzwerkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ## Sinus Generator
 * In dieser Klasse wird ein akustisches Signal mit einem Wave Generator und einem Interval generiert
 *
 * @author Klaus Buderer
 * @author Bruno Thurnherr
 *
 * @since 1.0.0
 *
 */
class SinusGenerator(application: Application) {

    lateinit var track: AudioTrack

    var frequenz: Int = 0
    var isPlaying: Boolean = false
    val netzwerkInfo: NetzwerkInfo = NetzwerkInfo(application)

    private val Fs: Int = 44100

    val buffLength: Int = AudioTrack.getMinBufferSize(
        Fs,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    /**
     * Starten des akustischen Signals
     * @since 1.0.0
     * @author Klaus Buderer
     */
    fun start(){
        initTrack()
        startPlaying()
        playback()
    }

    /**
     * Initialisierung des Audiotrack Objekts
     * @since 1.0.0
     * @author Klaus Buderer
     */
    private fun initTrack() {
             track = AudioTrack.Builder()
           .setAudioAttributes(AudioAttributes.Builder()
               .setUsage(AudioAttributes.USAGE_ALARM)
               .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
               .build())
           .setAudioFormat(AudioFormat.Builder()
               .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
               .setSampleRate(44100)
               .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
               .build())
           .setBufferSizeInBytes(buffLength)
           .build()
    }

    /**
     * Generierung des akustischen signals
     * @since 1.0.0
     * @author Klaus Buderer
     * @author Bruno Thurnherr
     */
    private fun playback() {
        // simple sine wave generator
        val frame_out: ShortArray = ShortArray(buffLength)
        var amplitude = 20000
        var frequency: Int = 100
        val twopi: Double = 8.0 * Math.atan(1.0)
        var phase: Double = 0.0
        track.playbackRate = 30000


        while (isPlaying) {
            frequency = 600 + frequenz
                for (i in 0 until buffLength) {

                    //frequency += 10
                    frame_out[i] = (amplitude * Math.sin(phase)).toInt().toShort()
                    phase += twopi * frequency / Fs
                    if (phase > twopi) {
                        phase -= twopi
                    }
                }
            Log.d("Amplitude",frequency.toString())
                track.write(frame_out, 0, buffLength)
        }
    }

    private fun startPlaying() {
        track.play()
        isPlaying = true
        intervallRoutine()
    }

    /**
     * Impulsgenerator -> Impulslänge in Relation zum Pegel
     * @since 1.0.0
     * @author Klaus Buderer
     * @author Bruno Thurnherr
     */
    private fun intervallRoutine(){
        CoroutineScope(Dispatchers.IO).launch {
            while (isPlaying == true){
                val rssi = netzwerkInfo.getConnectionInfo()!!.rssi
                track.setVolume(0.0F)
                delay(rssi * -1 * rssi * -1 / 8L)
                track.setVolume(1.0F)
                delay(rssi * -1 * rssi * -1 / 8L)
            }
        }
    }

     fun stopPlaying() {
         try {
             if (isPlaying) {
                 isPlaying = false
                 // Stop playing the audio data and release the resources
                 track.stop()
                 track.release()
             }
         }catch (e: IllegalStateException){
             Log.e("AudioTrack","Stop not successfull")
         }

    }
}