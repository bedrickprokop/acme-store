package com.acmestore.view.ui

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acmestore.Consts.FOUR_INT
import com.acmestore.Consts.KEY_USER
import com.acmestore.Consts.THREE_INT
import com.acmestore.Consts.ZERO_INT
import com.acmestore.R
import com.acmestore.databinding.ActSplashBinding
import com.acmestore.model.entity.User
import com.acmestore.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var bind: ActSplashBinding
    private var counter: Int = ZERO_INT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO Failing on tests
        // Hide status bar logic
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        //    window.insetsController?.hide(WindowInsets.Type.statusBars())
        //} else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //}

        // Inflating layout and viewModel setup
        bind = DataBindingUtil.setContentView(this, R.layout.act_splash)
        bind.viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        // Setup text version
        val appVersion = getString(R.string.act_splash_actv_version)
        bind.tvAppVersion.text = String.format(appVersion, BuildConfig.VERSION_NAME)

        // Logo click action to display version
        bind.ivLogo.setOnClickListener {
            counter++
            when (counter) {
                THREE_INT -> bind.tvAppVersion.visibility = View.VISIBLE
                FOUR_INT -> {
                    bind.tvAppVersion.visibility = View.INVISIBLE
                    counter = ZERO_INT
                }
            }
        }

        bind.viewModel?.getUserObservable()?.observe(this, getUserObserver())
    }

    private fun getUserObserver(): Observer<User> {
        return Observer { _user ->
            // Label button visibility
            bind.tvComeIn.visibility = View.VISIBLE

            // Label button animator
            val animatorSet = AnimatorInflater.loadAnimator(this, R.animator.text_blink_animator)
            animatorSet.setTarget(bind.tvComeIn)
            animatorSet.start()

            // Label button listener
            bind.tvComeIn.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(KEY_USER, _user)
                startActivity(Intent(this, MainActivity::class.java).putExtras(bundle))
            }
        }
    }

}