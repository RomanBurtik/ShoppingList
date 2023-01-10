package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemId: Int):ShopItem
    fun modifyShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun getShopList(): LiveData<List<ShopItem>>
}