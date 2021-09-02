package com.acmestore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acmestore.R
import com.acmestore.databinding.FragShopBinding
import com.acmestore.extension.dimenToPx
import com.acmestore.model.entity.Product
import com.acmestore.view.adapter.ProductListAdapter
import com.acmestore.view.adapter.SpaceDecoration
import com.acmestore.viewmodel.ShopViewModel

class ShopFragment : Fragment() {

    private lateinit var bind: FragShopBinding
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_shop, container, false)
        bind.viewModel = ViewModelProvider(requireActivity()).get(ShopViewModel::class.java)

        // TODO What it means ???
        // bind.lifecycleOwner = this

        bind.rvProducts.layoutManager = LinearLayoutManager(requireActivity())
        bind.rvProducts.addItemDecoration(SpaceDecoration(R.dimen.smaller_margin.dimenToPx(context)))
        adapter = ProductListAdapter(requireActivity()) {
            findNavController().navigate(
                HomeFragmentDirections.navHomeToProduct(it, Operation.BUY)
            )
        }
        bind.rvProducts.adapter = adapter
        return bind.root
    }

    override fun onResume() {
        super.onResume()
        bind.viewModel?.getAllProductsObservable()
            ?.observe(requireActivity(), getAllProductsObserver())
    }

    override fun onPause() {
        super.onPause()
        bind.pbLoading.visibility = View.VISIBLE
        bind.rvProducts.visibility = View.GONE
    }

    private fun getAllProductsObserver(): Observer<List<Product>> {
        return Observer {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                bind.pbLoading.visibility = View.GONE
                bind.rvProducts.visibility = View.VISIBLE
            }
        }
    }
}