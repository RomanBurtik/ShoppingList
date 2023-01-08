package com.example.shoppinglist.domain

class GetItemInShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId: Int):ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}