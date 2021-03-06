package com.acmestore.view.activity

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acmestore.Consts.FOUR_INT
import com.acmestore.Consts.KEY_USER
import com.acmestore.Consts.THREE_INT
import com.acmestore.Consts.ZERO_INT
import com.acmestore.R
import com.acmestore.databinding.ActSplashBinding
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.UserApi
import com.acmestore.model.entity.User
import com.acmestore.model.repository.impl.UserRepositoryImpl
import com.acmestore.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var bind: ActSplashBinding
    private val splashViewModel by viewModels<SplashViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val userApi = HttpApiGenerator<UserApi>().get(UserApi::class.java)
                val userRepository = UserRepositoryImpl(userApi)
                return SplashViewModel(userRepository) as T
            }
        }
    }
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

        // Inflate layout and viewModel setup
        bind = DataBindingUtil.setContentView(this, R.layout.act_splash)
        bind.viewModel = splashViewModel

        // Setup text version
        val appVersion = getString(R.string.act_splash_tv_version)
        bind.actvAppVersion.text = String.format(appVersion, BuildConfig.VERSION_NAME)

        // Logo click action to display version
        bind.acivLogo.setOnClickListener {
            counter++
            when (counter) {
                THREE_INT -> bind.actvAppVersion.visibility = View.VISIBLE
                FOUR_INT -> {
                    bind.actvAppVersion.visibility = View.INVISIBLE
                    counter = ZERO_INT
                }
            }
        }
        bind.viewModel?.userObservable?.observe(this, { showButtonEnter(it) })
        bind.viewModel?.errorObservable?.observe(this, { showError(it) })
        // TODO check if user exists in localstorage else find it in api service
        val owner = User(
            1,
            "Bedrick Prokop",
            "bedrick@mymail.com",
            1000000.00,
            arrayListOf()
        )
        bind.viewModel?.getUser(owner)
    }

    private fun showButtonEnter(user: User) {
        // Label button visibility
        bind.actvComeIn.visibility = View.VISIBLE

        // Label button animator
        val animatorSet = AnimatorInflater.loadAnimator(this, R.animator.text_blink_animator)
        animatorSet.setTarget(bind.actvComeIn)
        animatorSet.start()

        // Label button listener
        bind.actvComeIn.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(KEY_USER, user)
            startActivity(Intent(this, MainActivity::class.java).putExtras(bundle))
        }
    }

    private fun showError(errorMessage: String?) {
        // TODO show error message
    }
}