package com.gruppe4.wlan_detektor.model

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import kotlinx.coroutines.delay


class SinusGenerator(application: Application) {

    lateinit var track: AudioTrack

    var isPlaying: Boolean = false
    val netzwerkInfo: NetzwerkInfo = NetzwerkInfo(application)

    private val Fs: Int = 44100

    val buffLength: Int = AudioTrack.getMinBufferSize(
        Fs,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    fun start(frequenz: Int){
        initTrack()
        startPlaying()
        playback(frequenz)
    }


    private fun initTrack() {
        // Very similar to opening a stream in PyAudio
        // In Android create a AudioTrack instance and initialize it with different parameters

        // AudioTrack is deprecated for some android versions
        // Please look up for other alternatives if this does not work
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

       /* (
            AudioManager.MODE_NORMAL, Fs, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, buffLength, AudioTrack.MODE_STREAM
        )*/
    }

    private fun playback(frequenz: Int) {
        // simple sine wave generator
        val frame_out: ShortArray = ShortArray(buffLength)
        var amplitude = 20000
        var frequency: Int = 100
        val twopi: Double = 8.0 * Math.atan(1.0)
        var phase: Double = 0.0
        track.playbackRate = 30000


        while (isPlaying) {
            frequency = 100
             frequency -= (((netzwerkInfo.getConnectionInfo().rssi *-1) - 30) * 20)
                for (i in 0 until buffLength) {

                    //frequency += 10
                    frame_out[i] = (amplitude * Math.sin(phase)).toInt().toShort()
                    phase += twopi * frequency / Fs
                    if (phase > twopi) {
                        phase -= twopi
                    }
                }
            Log.e("Amplitude",frequency.toString())
                track.write(frame_out, 0, buffLength)
        }
    }

    private fun startPlaying() {
        track.play()
        isPlaying = true
    }

     fun stopPlaying() {
        if (isPlaying) {
            isPlaying = false
            // Stop playing the audio data and release the resources
            track.stop()
            track.release()
        }
    }
}