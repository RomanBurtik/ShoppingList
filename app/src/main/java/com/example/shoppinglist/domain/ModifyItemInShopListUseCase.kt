package com.example.shoppinglist.domain

class ModifyItemInShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun modifyShopItem(shopItem: ShopItem){
        shopListRepository.modifyShopItem(shopItem)
    }
}