package com.acmestore.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.acmestore.R
import com.acmestore.databinding.ActMainBinding
import com.acmestore.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating layout and viewModel setup
        bind = DataBindingUtil.setContentView(this, R.layout.act_main)
        bind.viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

}