package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun getShopItem(id: Int):ShopItem
    fun modifyShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun getShopList(): List<ShopItem>
}