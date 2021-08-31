package com.acmestore.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.acmestore.R
import com.acmestore.databinding.FragProductBinding
import com.acmestore.model.entity.Product

class ProductFragment : Fragment() {

    //private val args: ProductFragmentArgs by navArgs()
    private lateinit var bind: FragProductBinding

    // TODO remove temporary mock - only for tests
    private var product: Product? = Product(
        1,
        "Disintegrating pistol",
        240.32,
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
        "",
        null
    )

    // Here you can assign variables get intent extras and anything else that doesn't involve the
    // View hierarchy(non-graphical initialisations)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO convert product object to parcelable
        //product = activity?.intent?.getParcelableExtra<Product>("product")
    }

    // Here you can assign your View variables and do any graphical initializations
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_product, container, false)
        return bind.root
    }

    // Its mainly used for final initialisations(example, modifyinng ui elements)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.lifecycleOwner = this

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
            findNavController().navigateUp()
            return true
        }
        return false
    }

    private fun setupToolbar() {
        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(bind.mtToolbar)
        activity.supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            title = product?.name
        }
    }

    private fun setupView() {
        bind.mtvTitle.text = product?.name
        bind.mtvName.text = product?.name
        bind.mtvDescription.text = product?.description

        // TODO add a money mask here
        bind.mtvPrice.text = product?.unitPrice.toString()
        bind.mbPurchase.setOnClickListener {
            Toast.makeText(activity, "${product?.name}", Toast.LENGTH_SHORT).show()
        }
    }
}