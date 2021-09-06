package com.acmestore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.acmestore.Consts
import com.acmestore.R
import com.acmestore.databinding.FragHomeBinding
import com.acmestore.view.vo.ProductOperation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

private const val INDEX_SHOP = 0
private const val INDEX_INVENTORY = 1
private const val INDEX_CONFIG = 2
private const val NUM_TABS = 3

private val TAB_TITLES = mapOf(
    INDEX_SHOP to "shop",
    INDEX_INVENTORY to "invent",
    INDEX_CONFIG to "config"
)

class HomeFragment : Fragment() {

    private lateinit var bind: FragHomeBinding
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupTabLayout()
    }

    override fun onResume() {
        super.onResume()
        showMessage()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(bind.tToolbar)
    }

    private fun setupTabLayout() {
        bind.vpPager.adapter = TabsAdapter(childFragmentManager, lifecycle)

        // Connect the tabs and view pager2
        TabLayoutMediator(bind.tlTabs, bind.vpPager) { tab, position ->
            tab.text = TAB_TITLES[position]
            bind.vpPager.setCurrentItem(tab.position, true)
        }.attach()
        bind.tlTabs.getTabAt(INDEX_SHOP)?.let { bind.tlTabs.selectTab(it) }
    }

    private fun showMessage() {
        args.product?.let {
            var message: String = Consts.EMPTY
            when (args.resultOperation) {
                ProductOperation.ADD_CART -> message =
                    getString(R.string.frag_home_operation_shopping_cart_message)
                ProductOperation.BUY -> {
                    // TODO
                }
                ProductOperation.SELL -> message =
                    getString(R.string.frag_home_operation_sell_message)
            }
            Snackbar.make(
                bind.clContainer,
                String.format(message, args.product?.name),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}

class TabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            INDEX_SHOP -> ShopFragment()
            INDEX_INVENTORY -> InventoryFragment()
            INDEX_CONFIG -> ConfigFragment()
            else -> ShopFragment()
        }
    }
}