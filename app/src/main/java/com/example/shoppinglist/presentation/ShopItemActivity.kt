package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel : ShopItemViewModel
    private lateinit var tilName:TextInputLayout
    private lateinit var tilCount:TextInputLayout
    private lateinit var etName:EditText
    private lateinit var etCount:EditText
    private lateinit var buttonSave:Button

    private var screenMode= MODE_UNKNOWN
    private var shopItemId=ShopItem.UndefinedId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()

        addTextCountChangeListeners()

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        в зависимости от значения будет вызван метод, который определяет поведение кнопки.
        when (screenMode){
            MODE_EDIT->launchEditMode()
            MODE_ADD->launchAddMode()
        }
        inputErrorObservers()
        viewModel.killedActivity.observe(this){
            finish()
        }

    }

    private fun inputErrorObservers() {
        //        вставляю текст ошибки в TextInputLayout
        viewModel.errorInputCount.observe(this) {
    //            в vm я написал уже проверки полей. здесь мне нужно узнать результат проверки и отреагировать.
    //            it - true / false value of mld _errorInputCount
            val message = if (it) {
                getString(R.string.tilCountErrorMessage)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.tilNameErrorMessage)
            } else {
                null
            }
            tilName.error = message
        }
    }

    private fun addTextCountChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    //                TODO("Not yet implemented") необязательно
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
    //                TODO("Not yet implemented") необязательно
            }

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    //                TODO("Not yet implemented") необязательно
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
    //                TODO("Not yet implemented") необязательно
            }

        })
    }

    private fun launchEditMode(){
//        val originalText=etName.setText(intent.getStringExtra(EXTRA_SHOP_ITEM_TEXT)).toString()
//        val originalCount=etCount.setText(intent.getStringExtra(EXTRA_SHOP_ITEM_COUNT)).toString()
//        buttonSave.setOnClickListener {
//            viewModel.editShopItem(originalText,originalCount)
//        }
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            etName.setText(it.name)
            etCount.setText(it.count).toString()
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(),etCount.text.toString())
        }

    }

    private fun launchAddMode(){
//        tilName.error = R.string.tilNameErrorMessage.toString()
//        tilCount.error = R.string.tilCountErrorMessage.toString()
//        val inputText=etName.text.toString()
//        val inputCount=etCount.text.toString()
//        buttonSave.setOnClickListener {
//            viewModel.addItemToShopList(inputText,inputCount)
//            viewModel.killedActivity.observe(this){
//                finish()
//            }
//        }
        buttonSave.setOnClickListener {
            viewModel.addItemToShopList(etName.text.toString(),etCount.text.toString())
        }
    }

    private fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode!= MODE_EDIT && mode!= MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode=mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item hasn't been fount")
            }
            shopItemId=intent.getIntExtra(EXTRA_SHOP_ITEM_ID,ShopItem.UndefinedId)
        }
    }

    private fun initViews(){
        tilName=findViewById<TextInputLayout>(R.id.tilName)
        tilCount=findViewById<TextInputLayout>(R.id.tilCount)
        etName=findViewById<EditText>(R.id.etName)
        etCount=findViewById<EditText>(R.id.etCount)
        buttonSave=findViewById<Button>(R.id.button_save)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EXTRA_SHOP_ITEM_TEXT = "extra_shop_item_text"
        private const val EXTRA_SHOP_ITEM_COUNT = "extra_shop_item_count"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItem: ShopItem): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItem.id)
            intent.putExtra(EXTRA_SHOP_ITEM_TEXT, shopItem.name)
            intent.putExtra(EXTRA_SHOP_ITEM_COUNT, shopItem.count)
            return intent
        }
    }
}

