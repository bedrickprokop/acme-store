package com.acmestore.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.acmestore.R
import com.acmestore.databinding.FragProductBinding
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.entity.Product
import com.acmestore.model.repository.impl.ProductRepositoryImpl
import com.acmestore.view.vo.ProductOperation
import com.acmestore.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private lateinit var bind: FragProductBinding
    private val productViewModel by viewModels<ProductViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val productApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)
                val productRepository = ProductRepositoryImpl(productApi)
                return ProductViewModel(productRepository) as T
            }
        }
    }
    private val args: ProductFragmentArgs by navArgs()

    // Here you can assign variables get intent extras and anything else that doesn't involve the
    // View hierarchy(non-graphical initialisations)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Here you can assign your View variables and do any graphical initializations
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_product, container, false)
        bind.viewModel = productViewModel
        productViewModel.addToCartObservable.observe(requireActivity(), { showSuccess(it) })
        productViewModel.sellObservable.observe(requireActivity(), { showSuccess(it) })
        productViewModel.errorObservable.observe(requireActivity(), { showError(it) })
        return bind.root
    }

    // Its mainly used for final initialisations(example, modifying ui elements)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupToolbar()
        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigate(
                ProductFragmentDirections.navProductToHome(null, null)
            )
            return true
        }
        return false
    }

    private fun setupToolbar() {
        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(bind.tToolbar)
        activity.supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            title = args.product.name
        }
    }

    private fun setupView() {
        bind.actvTitle.text = args.product.name
        bind.actvName.text = args.product.name
        bind.actvDescription.text = args.product.description
        // TODO add a money mask here
        bind.actvPrice.text = args.product.unitPrice.toString()

        when (args.actionOperation) {
            ProductOperation.ADD_CART -> {
                bind.acbAddCart.visibility = View.VISIBLE
                bind.acbAddCart.setOnClickListener { bind.viewModel?.addToCart(args.product) }
            }
            ProductOperation.SELL -> {
                bind.acbSell.visibility = View.VISIBLE
                bind.acbSell.setOnClickListener { bind.viewModel?.sell(args.product) }
            }
        }
    }

    private fun showSuccess(product: Product) {
        findNavController().navigate(
            ProductFragmentDirections.navProductToHome(product, args.actionOperation)
        )
    }

    private fun showError(message: String?) {
        // TODO show error message
    }
}