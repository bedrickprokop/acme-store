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
import com.acmestore.databinding.FragInventoryBinding
import com.acmestore.extension.dimenToPx
import com.acmestore.model.data.StateData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.view.adapter.ProductListAdapter
import com.acmestore.view.adapter.SpaceDecoration
import com.acmestore.view.vo.ProductOperation
import com.acmestore.viewmodel.InventoryViewModel

class InventoryFragment : Fragment() {

    private lateinit var bind: FragInventoryBinding
    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(requireActivity()) {
            findNavController().navigate(
                HomeFragmentDirections.navHomeToProduct(it, ProductOperation.SELL)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_inventory, container, false)
        bind.viewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.rvInventory.layoutManager = LinearLayoutManager(requireActivity())
        bind.rvInventory.addItemDecoration(SpaceDecoration(R.dimen.smaller_margin.dimenToPx(context)))
        bind.rvInventory.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // TODO check if user exists in localstorage else find it in api service
        val owner = User(
            1,
            "Bedrick Prokop",
            "bedrick@mymail.com",
            1000000.00,
            arrayListOf()
        )
        bind.viewModel?.getInventoryObservable(owner)
            ?.observe(requireActivity(), getInventoryObserver())
    }

    override fun onPause() {
        super.onPause()
        bind.pbLoading.visibility = View.VISIBLE
        bind.rvInventory.visibility = View.GONE
    }

    private fun getInventoryObserver(): Observer<in StateData<List<Product>>?> {
        return Observer {
            when (it?.status) {
                StateData.DataStatus.SUCCESS -> proceedSuccessFlow(it.data!!)
                else -> proceedErrorFlow(it?.error?.message)
            }
        }
    }

    private fun proceedSuccessFlow(inventoryList: List<Product>) {
        if (inventoryList.isNotEmpty()) {
            adapter.submitList(inventoryList)
            bind.pbLoading.visibility = View.GONE
            bind.rvInventory.visibility = View.VISIBLE
        }
    }

    private fun proceedErrorFlow(message: String?) {
        // TODO show error message
    }
}