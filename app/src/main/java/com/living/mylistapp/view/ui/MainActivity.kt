package com.living.mylistapp.view.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.living.mylistapp.R
import com.living.mylistapp.model.Item
import com.living.mylistapp.view.adapter.ItemAdapter
import com.living.mylistapp.viewModel.ItemViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), View.OnClickListener,ItemAdapter.ItemListener, DialogDelete.onClickListenerDialog {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var rcv: RecyclerView
    private lateinit var edtSearch: EditText
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var adapter: ItemAdapter
    companion object {
        private var imgUrls = mutableListOf(
                "https://cdn1.iconfinder.com/data/icons/logos-brands-in-colors/231/among-us-player-green-512.png",
                "https://cdn1.iconfinder.com/data/icons/logos-brands-in-colors/231/among-us-player-pink-512.png",
                "https://cdn1.iconfinder.com/data/icons/logos-brands-in-colors/231/among-us-player-orange-512.png"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        bindingView()

        itemViewModel.onLoadItems().observe(this, Observer {
            setupRecyclerView(it)
        })

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })

        fabAdd.setOnClickListener(this)
    }

    fun bindingView() {
        rcv = findViewById(R.id.rcv)
        edtSearch = findViewById(R.id.edt_search)
        fabAdd = findViewById(R.id.fab_add)

        rcv.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rcv.addItemDecoration(itemDecor)
    }

    private fun setupRecyclerView(list: List<Item>) {

        adapter = get { parametersOf(list, this) }
        rcv.adapter = adapter
        adapter.setListener()
    }

    override fun onClick(v: View?) {
        imgUrls.shuffle()
        val imgUrl = imgUrls.last()

        itemViewModel.onAddItem(imgUrl).observe(this, Observer {
            setupRecyclerView(it)
        })
    }

    override fun onItemClicked(itemID: Int) {
        var dialog: DialogDelete = get { parametersOf(this,itemID, this) }
        dialog.setDialogListener()
    }

    override fun onOK(dialog: Dialog, itemID: Int) {
        itemViewModel.onDelete(itemID)
        rcv.adapter.let {
            it?.notifyDataSetChanged()
        }
        dialog.dismiss()
    }

    override fun onCancel(dialog: Dialog) {
        dialog.dismiss()
    }
}
