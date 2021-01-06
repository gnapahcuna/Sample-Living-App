package com.living.mylistapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.living.mylistapp.model.Item

class ItemViewModel: ViewModel() {

    private var itemList: MutableLiveData<MutableList<Item>> = MutableLiveData()
    private var images = mutableListOf<String>()

    init {
        images.add("https://cdn1.iconfinder.com/data/icons/logos-brands-in-colors/231/among-us-player-red-512.png")
        images.add("https://cdn1.iconfinder.com/data/icons/logos-brands-in-colors/231/among-us-player-light-blue-512.png")
    }

    fun getRandomItemName(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }

    fun onLoadItems(): LiveData<MutableList<Item>> {
        val items: MutableList<Item> = mutableListOf()
        for (i in 0..images.size - 1) {
            items.add(Item(i, getRandomItemName(6), images.get(i)))
        }
        itemList.value = items

        return itemList
    }


    fun onAddItem(imgUrl: String): LiveData<MutableList<Item>> {
        val _items = itemList.value


        val _id: Int
        if(_items!!.isEmpty()){
            _id = 0
        }else{
            _id = _items?.last()?.itemID!! + 1
        }


        _items.add(Item(_id, getRandomItemName(6), imgUrl))
        itemList.value = _items

        return itemList
    }


    fun onDelete(id: Int) {
        val item = itemList.value?.find { it -> it.itemID == id }
        itemList.value?.remove(item)
    }

}