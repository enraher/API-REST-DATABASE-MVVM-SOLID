package com.enraher.githublist.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {
    private var _splashTimeElapsed: MutableLiveData<Boolean> = MutableLiveData()
    val splashTimeElapsed: LiveData<Boolean> = _splashTimeElapsed

    init {

        val timer = object: CountDownTimer(SPLASH_TIME, SPLASH_TIME) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                _splashTimeElapsed.value = true
            }
        }
        timer.start()
    }

    companion object {
        private const val SPLASH_TIME = 1000L
    }
}