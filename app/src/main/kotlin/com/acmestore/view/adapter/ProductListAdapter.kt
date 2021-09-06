package com.acmestore.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acmestore.R
import com.acmestore.databinding.ItemProductListBinding
import com.acmestore.model.entity.Product

class ProductListAdapter(
    private val context: Context,
    private val listener: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var mDiffer: AsyncListDiffer<Product> = AsyncListDiffer(this, ItemCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = DataBindingUtil.inflate<ItemProductListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_product_list,
            parent,
            false
        )
        return ViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind.clContainer.setOnClickListener { listener(product) }
        holder.bind.actvName.text = product.name
        holder.bind.actvPrice.text =
            context.getString(R.string.item_product_list_price_text, product.unitPrice.toString())

        // TODO load image from url and set promotion text
        //holder.bind.actvPromotion.text =
        //holder.bind.acivImage =
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    public fun submitList(data: List<Product>) = mDiffer.submitList(data)

    public fun getItem(position: Int): Product = mDiffer.currentList[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bind: ItemProductListBinding = DataBindingUtil.bind(itemView)!!
    }

    inner class ItemCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }
}