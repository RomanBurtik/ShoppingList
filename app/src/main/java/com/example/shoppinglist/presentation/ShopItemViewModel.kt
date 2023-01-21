package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val addItemToShopListUseCase = AddItemToShopListUseCase(repository)
    private val getItemInShopListUseCase = GetItemInShopListUseCase(repository)
    private val modifyItemInShopListUseCase = ModifyItemInShopListUseCase(repository)

//    чтобы с объектом можно было работать только здесь, объявляю private.
    private val _errorInputName = MutableLiveData<Boolean>()
//    в активности нужно будет подписать на эту LD, но нельзя её менять. для этого создаю public переменную, но без _ и переопределяют геттер.
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

//    Данная LD может быть типа Boolean, но я ей везде устанавливаю true, поэтому меняю тип на Unit (данный объект мне нужен только чтобы на него подписаться
//    данный объект мне нужен только чтобы на него подписаться и закрыть экран.
    private val _killedActivity = MutableLiveData<Unit>()
    val killedActivity: LiveData<Unit>
        get() = _killedActivity

    //    для того, чтобы в активити было меньше текста, я здесь написал всю реализацию данного метода.
    fun addItemToShopList(inputName: String, inputCount: String) {
        val name = inputName.trim()
        val count = inputCount.trim().toInt()
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            addItemToShopListUseCase.addShopItem(ShopItem(name, count, true))
            killActivity()
        }
    }

    fun getShopItem(shopItemId: Int) {
        val item = getItemInShopListUseCase.getShopItem(shopItemId)
        _shopItem.value=item
        killActivity()
    }

    fun editShopItem(textFromShopItem: String, countFromShopItem: String) {
        val name = textFromShopItem.trim()
        val count = countFromShopItem.trim().toInt()
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
//            из mld объекта я получаю свою заметку. а могу и не получить, поэтому проверка на null и с помощью let копирую данные
//            у нулабельных объектом нельзя скопировать, поэтому let. так же копируется параметр активности элемента, что очень хорошо.
            _shopItem.value?.let {
                val item=it.copy(name=name,count=count)
                modifyItemInShopListUseCase.modifyShopItem(item)
                killActivity()
            }
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    //    Этот метод написан, чтобы после отображения ошибки, при вводе данных, ошибка ушла.
    fun resetErrorInputName() {
        _errorInputName.value = false
    }
    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }
//    Этот метод говорит активити, что можно закрыть прошлую активити.
    private fun killActivity() {
        _killedActivity.value = Unit
    }
}