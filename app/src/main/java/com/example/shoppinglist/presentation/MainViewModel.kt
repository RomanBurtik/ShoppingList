package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class MainViewModel: ViewModel() {

    private val repository=ShopListRepositoryImpl

    private val getShopListUseCase=GetShopListUseCase(repository)
    private val deleteFromShopListUseCase=DeleteFromShopListUseCase(repository)
    private val addItemToShopListUseCase=AddItemToShopListUseCase(repository)
    private val getItemInShopListUseCase=GetItemInShopListUseCase(repository)
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