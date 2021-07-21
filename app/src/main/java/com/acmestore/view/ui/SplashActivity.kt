package com.acmestore.view.ui

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.acmestore.*
import com.acmestore.databinding.ActSplashBinding
import com.acmestore.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var bind: ActSplashBinding
    private var counter: Int = ZERO_INT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide status bar logic
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // Inflating layout and viewModel setup
        bind = DataBindingUtil.setContentView(this, R.layout.act_splash)
        bind.viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        // Setup text version
        val appVersion = getString(R.string.act_splash_actv_version)
        bind.actvAppVersion.text = String.format(appVersion, BuildConfig.VERSION_NAME)

        // Logo click action to display version
        bind.acivLogo.setOnClickListener {
            counter++
            when (counter) {
                THREE_INT -> bind.actvAppVersion.visibility = View.VISIBLE
                FOUR_INT -> {
                    bind.actvAppVersion.visibility = View.GONE
                    counter = ZERO_INT
                }
            }
        }

        // Label button animator
        val animatorSet = AnimatorInflater.loadAnimator(this, R.animator.text_blink_animator)
        animatorSet.setTarget(bind.actvComeIn)
        animatorSet.start()

        // Label button click action
        bind.actvComeIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}