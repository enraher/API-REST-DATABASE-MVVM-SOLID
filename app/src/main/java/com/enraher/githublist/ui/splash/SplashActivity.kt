package com.enraher.githublist.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.enraher.githublist.R
import com.enraher.githublist.ui.repos.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_splash)

        if (splashViewModel.splashTimeElapsed.value == true) navigateToMainActivity()

        splashViewModel.splashTimeElapsed.observe(this) { splashTimeElapsed ->
            if (splashTimeElapsed == true)
                navigateToMainActivity()
        }
    }


    private fun navigateToMainActivity() {
        val intent = MainActivity.createIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}