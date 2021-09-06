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
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.acmestore.Consts
import com.acmestore.R
import com.acmestore.databinding.FragHomeBinding
import com.acmestore.model.entity.Product
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

// TODO Test: short syntax for fragment creation. Enabled by androidx.navigation:navigation-fragment-ktx dependency
class HomeFragment : Fragment(R.layout.frag_home) {

    private lateinit var bind: FragHomeBinding
    private var product: Product? = null
    private var resultOperation: ProductOperation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable(Consts.KEY_PRODUCT)
        resultOperation = arguments?.getParcelable(Consts.KEY_RESULT_OPERATION)
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
        product?.let {
            var message: String = Consts.EMPTY
            when (resultOperation) {
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
                String.format(message, product?.name),
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