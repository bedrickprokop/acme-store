package com.acmestore.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.acmestore.R
import com.acmestore.databinding.ActMainBinding
import com.acmestore.view.fragment.AboutMeFragment
import com.acmestore.view.fragment.MyProductsFragment
import com.acmestore.view.fragment.SettingsFragment
import com.acmestore.view.fragment.ShopFragment
import com.acmestore.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

private const val TAG_ABOUT_ME_FRAGMENT = "about_me_fragment"
private const val TAG_MY_PRODUCTS_FRAGMENT = "my_products_fragment"
private const val TAG_SHOP_FRAGMENT = "shop_fragment"
private const val TAG_SETTINGS_FRAGMENT = "settings_fragment"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var bind: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide status bar logic
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Inflate layout and viewModel setup
        bind = DataBindingUtil.setContentView(this, R.layout.act_main)
        bind.viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Setup toolbar
        updateToolbarTitle(getString(R.string.frag_shop_title))
        setSupportActionBar(bind.mtAction)

        // Setup navigation view
        bind.nvContainer.setNavigationItemSelectedListener(this)

        // Setup drawer icon and toggle state
        val toggle = ActionBarDrawerToggle(
            this, bind.dlContainer, bind.mtAction,
            R.string.act_main_nv_lbl_open_drawer, R.string.act_main_nv_lbl_close_drawer
        )
        bind.dlContainer.addDrawerListener(toggle)
        toggle.syncState()

        // Setup default fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ShopFragment(), TAG_SHOP_FRAGMENT).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_about_me -> {
                updateToolbarTitle(getString(R.string.frag_about_me_title))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, AboutMeFragment(), TAG_ABOUT_ME_FRAGMENT).commit()
            }
            R.id.item_my_products -> {
                updateToolbarTitle(getString(R.string.frag_my_products_title))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, MyProductsFragment(), TAG_MY_PRODUCTS_FRAGMENT)
                    .commit()
            }
            R.id.item_shop -> {
                updateToolbarTitle(getString(R.string.frag_shop_title))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, ShopFragment(), TAG_SHOP_FRAGMENT).commit()
            }
            R.id.item_settings -> {
                updateToolbarTitle(getString(R.string.frag_settings_title))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, SettingsFragment(), TAG_SETTINGS_FRAGMENT).commit()
            }
        }
        bind.dlContainer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun updateToolbarTitle(title: String) {
        bind.mtAction.title = title
    }

    override fun onBackPressed() {
        if (bind.dlContainer.isDrawerOpen(GravityCompat.START)) bind.dlContainer.closeDrawer(
            GravityCompat.START
        )
        else super.onBackPressed()
    }
}