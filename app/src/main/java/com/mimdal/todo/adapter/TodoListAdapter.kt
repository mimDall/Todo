package com.mimdal.todo.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimdal.todo.data.model.Priority
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.databinding.TodoItemBinding
import com.mimdal.todo.util.Util
import java.util.ArrayList


class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
//
//    var dataList = Util.backUpList
    var dataList = emptyList<Todo>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val recyclerItemBinding =
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("TodoListAdapter", "onCreateViewHolder is called")
        return TodoViewHolder(recyclerItemBinding)
    }


    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("TodoListAdapter", "payloads size: ${payloads.isEmpty()}")
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)

        } else if (payloads.isNotEmpty()) {

            val bundle = payloads[0] as Bundle

            for (i in bundle.keySet()) {
                Log.d("TodoListAdapter", "key is: $i")

                if (i == "title") {
                    holder.bindTitle(bundle.getString("title", ""))
                    Log.d("TodoListAdapter", "title fired. new ${bundle.getString("title", "")}")
                }

                if (i == "description") {
                    holder.bindDescription(bundle.getString("description", ""))

                    Log.d(
                        "TodoListAdapter",
                        "description fired. new: ${bundle.getString("description", "")}"
                    )

                }
            }
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(dataList[position])
        Log.d("TodoListAdapter", "item fired")
//        setFadeAnimation(holder.itemView)

    }


    private fun setFadeAnimation(view: View) {
        val anim = ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        anim.duration = 500
        view.startAnimation(anim)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }


    fun setData(data: List<Todo>) {
        val todoDiffUtil = TodoDiffUtil(dataList, data)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        dataList=data
        todoDiffResult.dispatchUpdatesTo(this)
    }


    class TodoViewHolder(val recyclerItemBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(recyclerItemBinding.root) {

        fun bind(todo: Todo) {
            recyclerItemBinding.todoItem = todo
            recyclerItemBinding.executePendingBindings()
        }

        fun bindTitle(title: String) {
            recyclerItemBinding.todoItem!!.title = title
            recyclerItemBinding.executePendingBindings()
        }

        fun bindDescription(description: String) {
            recyclerItemBinding.todoItem!!.description = description
            recyclerItemBinding.executePendingBindings()
        }

    }


}