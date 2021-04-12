package com.mimdal.todo.adapter

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.mimdal.todo.data.model.Todo

class TodoDiffUtil(private val oldList: List<Todo>, private val newList: List<Todo>) :
    DiffUtil.Callback() {


    override fun getOldListSize(): Int {

        Log.d("TodoDiffUtil", "getOldListSize: ${oldList.size}")
        return oldList.size
    }

    override fun getNewListSize(): Int {
        Log.d("TodoDiffUtil", "getNewListSize: ${newList.size}")

        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        var res = false

        if (oldList[oldItemPosition].id === newList[newItemPosition].id) {
            res = true
        }
        Log.d("TodoDiffUtil", "areItemsTheSame: $res oldPositon: $oldItemPosition newPosition: $newItemPosition")
        return res
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        var res = false

        if (oldList[oldItemPosition].id == newList[newItemPosition].id
            && oldList[oldItemPosition].title == newList[newItemPosition].title
            && oldList[oldItemPosition].priority == newList[newItemPosition].priority
            && oldList[oldItemPosition].description == newList[newItemPosition].description
        ) {
            res = true
        }

        Log.d("TodoDiffUtil", "areContentsTheSame: $res")
        return res

    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[newItemPosition]
//        val bundle = Bundle()
//
//        if (newItem.title != oldItem.title) {
//
//            bundle.putString("title", newItem.title)
//        }
//
//        Log.d("TodoDiffUtil", "oldItemPosition: $oldItemPosition")
//        Log.d("TodoDiffUtil", "newItemPosition: $newItemPosition")
//
//
//        if (newItem.description != oldItem.description) {
//            bundle.putString("description", newItem.description)
//
//        }
//
////        if (oldItem.title != newItem.title) {
////        }
////        if (oldItem.description != newItem.description) {
////
////        }
//
//        return bundle
//    }
}