package com.living.mylistapp

import android.content.Context
import android.widget.Adapter
import com.living.mylistapp.model.Item
import com.living.mylistapp.view.adapter.ItemAdapter
import com.living.mylistapp.view.ui.DialogDelete
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var ItemModule = module {
    factory { (data: List<Item>, view: ItemAdapter.ItemListener) -> ItemAdapter(androidContext(), data, view) }
    factory { (context: Context,itemID: Int, view: DialogDelete.onClickListenerDialog) -> DialogDelete(context, itemID, view)  }
}