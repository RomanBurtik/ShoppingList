package com.example.shoppinglist.domain

data class ShopItem(val name: String, val count: Int, val isActive: Boolean, var id : Int = UndefinedId) {
    companion object {
        const val UndefinedId=-1
    }
}
