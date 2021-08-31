package com.acmestore.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.acmestore.R
import com.google.android.material.tabs.TabLayout
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

// Short syntax for fragment creation. Enabled by androidx.navigation:navigation-fragment-ktx dependency
class HomeFragment : Fragment(R.layout.frag_home) {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.mt_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val tabLayout: TabLayout = view.findViewById(R.id.tl_tabs)
        viewPager = view.findViewById(R.id.vp_pager)
        viewPager.adapter = TabsAdapter(childFragmentManager, lifecycle)

        // Connect the tabs and view pager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
        tabLayout.getTabAt(INDEX_SHOP)?.let { tabLayout.selectTab(it) }
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