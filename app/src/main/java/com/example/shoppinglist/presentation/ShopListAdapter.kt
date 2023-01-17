package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ACTIVE_SHOP_ITEM -> R.layout.item_shop_enabled
            INACTIVE_SHOP_ITEM -> R.layout.item_shop_disabled
            else -> {
                throw RuntimeException("Unknown view type: $viewType")
            }
        }
        val view =
            LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.itemView.setOnLongClickListener{
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        holder.tvNumber.text=shopItem.count.toString()
        holder.tvProduct.text=shopItem.name
    }

    override fun getItemViewType(position: Int): Int {
        val itemInShopList = getItem(position)
        return if (itemInShopList.isActive) {
            ACTIVE_SHOP_ITEM
        } else INACTIVE_SHOP_ITEM
    }

    companion object {
        const val INACTIVE_SHOP_ITEM = 100
        const val ACTIVE_SHOP_ITEM = 101
        const val MAX_POOL_SIZE = 15
    }
}