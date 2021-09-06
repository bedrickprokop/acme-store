package com.acmestore.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acmestore.Consts.KEY_DESIRED_OPERATION
import com.acmestore.Consts.KEY_PRODUCT
import com.acmestore.R
import com.acmestore.databinding.FragProductBinding
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.view.vo.ProductOperation
import com.acmestore.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    //TODO test with this private val args: ProductFragmentArgs by navArgs()
    private lateinit var bind: FragProductBinding
    private var product: Product? = null
    private var desiredOperation: ProductOperation? = null

    // Here you can assign variables get intent extras and anything else that doesn't involve the
    // View hierarchy(non-graphical initialisations)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable(KEY_PRODUCT)
        desiredOperation = arguments?.getParcelable(KEY_DESIRED_OPERATION)
    }

    // Here you can assign your View variables and do any graphical initializations
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_product, container, false)
        bind.viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
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
            title = product?.name
        }
    }

    private fun setupView() {
        bind.actvTitle.text = product?.name
        bind.actvName.text = product?.name
        bind.actvDescription.text = product?.description
        // TODO add a money mask here
        bind.actvPrice.text = product?.unitPrice.toString()

        when (desiredOperation) {
            ProductOperation.ADD_CART -> {
                bind.acbAddCart.visibility = View.VISIBLE
                bind.acbAddCart.setOnClickListener {

                    // TODO add current user to product
                    product?.owner = User(
                        1,
                        "Bedrick Prokop",
                        "bedrick@mymail.com",
                        1000000.00,
                        arrayListOf()
                    )
                    bind.viewModel?.addToCartObservable(product!!)
                        ?.observe(requireActivity(), addToCartObserver())
                }
            }
            ProductOperation.SELL -> {
                bind.acbSell.visibility = View.VISIBLE
                bind.acbSell.setOnClickListener {
                    // TODO create sell functionality
                    //product?.owner = null
                    //ProductFragmentDirections.natProductToHome()
                }
            }
        }
    }

    private fun addToCartObserver(): Observer<Product> {
        return Observer {
            findNavController().navigate(
                ProductFragmentDirections.navProductToHome(it, ProductOperation.ADD_CART)
            )
        }
    }
}