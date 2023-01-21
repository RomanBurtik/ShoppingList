package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteFromShopListUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ModifyItemInShopListUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository=ShopListRepositoryImpl

    private val getShopListUseCase=GetShopListUseCase(repository)
    private val deleteFromShopListUseCase=DeleteFromShopListUseCase(repository)
    private val modifyItemInShopListUseCase=ModifyItemInShopListUseCase(repository)

    val shopList=getShopListUseCase.getShopList()

    fun deleteFromShopList(shopItem: ShopItem){
        deleteFromShopListUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem=shopItem.copy(isActive = !shopItem.isActive)
        modifyItemInShopListUseCase.modifyShopItem(newItem)

    }
}