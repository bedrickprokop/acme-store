package com.acmestore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acmestore.R
import com.acmestore.databinding.FragShopBinding
import com.acmestore.extension.dimenToPx
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.entity.Product
import com.acmestore.model.repository.impl.ProductRepositoryImpl
import com.acmestore.view.adapter.ProductListAdapter
import com.acmestore.view.adapter.SpaceDecoration
import com.acmestore.view.vo.ProductOperation
import com.acmestore.viewmodel.ShopViewModel

class ShopFragment : Fragment() {

    private lateinit var bind: FragShopBinding

    private val shopViewModel by viewModels<ShopViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val productApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)
                val productRepository = ProductRepositoryImpl(productApi)
                return ShopViewModel(productRepository) as T
            }
        }
    }

    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(requireActivity()) {
            findNavController().navigate(
                HomeFragmentDirections.navHomeToProduct(it, ProductOperation.ADD_CART)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_shop, container, false)
        bind.viewModel = shopViewModel
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.rvProducts.layoutManager = LinearLayoutManager(requireActivity())
        bind.rvProducts.addItemDecoration(SpaceDecoration(R.dimen.smaller_margin.dimenToPx(context)))
        bind.rvProducts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        bind.viewModel?.allProductsObservable?.observe(requireActivity(), { showAllProducts(it) })
        bind.viewModel?.errorObservable?.observe(requireActivity(), { showError(it) })
        bind.viewModel?.getAllProducts()
    }

    override fun onPause() {
        super.onPause()
        bind.pbLoading.visibility = View.VISIBLE
        bind.rvProducts.visibility = View.GONE
    }

    private fun showAllProducts(productList: List<Product>) {
        if (productList.isNotEmpty()) {
            adapter.submitList(productList)
            bind.pbLoading.visibility = View.GONE
            bind.rvProducts.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String?) {
        // TODO show error message
    }
}