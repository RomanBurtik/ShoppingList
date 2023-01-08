package com.example.shoppinglist.domain

class GetItemInShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(id: Int):ShopItem{
        return shopListRepository.getShopItem(id)
    }
}