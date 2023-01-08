package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId=0

//    для того, чтобы можно было добавить объект с тем же id, я создаю проверку:
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id==ShopItem.UndefinedId){
//          айди равен значению autoIncrementId и после этого действия данная переменная увеличивается на единицу
            shopItem.id= autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element with $shopItemId not found")
    }

//    в данный метод прилетает объект shopItem. я его удаляю и на его место новый объект вставляю. потому что нету базы данных.
    override fun modifyShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toMutableList()
    }

}