package com.mimdal.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.mimdal.todo.R
import com.mimdal.todo.data.model.SpinnerPriority
import com.mimdal.todo.databinding.SpinnerItemCloseBinding
import com.mimdal.todo.databinding.SpinnerItemOpenBinding

class CustomSpinner(context: Context, priorityList: ArrayList<SpinnerPriority>) :
    ArrayAdapter<SpinnerPriority>(context, 0, priorityList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindViewOpen(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindViewOpen(position, parent)
    }

    private fun onBindViewOpen(position: Int, parent: ViewGroup): View {

        val spinnerItem = getItem(position)
        val view: SpinnerItemOpenBinding =
//            SpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.spinner_item_open,
                parent,
                false
            )
        view.spinnerItem = spinnerItem

        return view.root

    }

    private fun onBindClose(position: Int, parent: ViewGroup): View {
        return SpinnerItemCloseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root
    }
}