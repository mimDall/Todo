package com.mimdal.todo.bindingAdapter

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.mimdal.todo.R
import com.mimdal.todo.data.model.Priority
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.fragments.ListFragmentDirections
import kotlinx.android.synthetic.main.todo_item.view.*

class BindingAdapter {


    companion object {

        @BindingAdapter("parsePriorityToSpinnerItemSelect")
        @JvmStatic
        fun parsePriorityToSpinnerItemSelect(spinner: Spinner, priority: Priority) {
            when (priority) {
                Priority.LOW -> spinner.setSelection(2)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.HIGH -> spinner.setSelection(0)
            }
        }

        @BindingAdapter("parsePriorityToColor")
        @JvmStatic
        fun parsePriorityToColor(imageView: ImageView, priority:Priority){
            when(priority){

                Priority.HIGH -> (imageView.background as GradientDrawable).setColor(ContextCompat.getColor(imageView.context, R.color.red))
                Priority.MEDIUM -> (imageView.background as GradientDrawable).setColor(ContextCompat.getColor(imageView.context, R.color.yellow))
                Priority.LOW -> (imageView.background as GradientDrawable).setColor(ContextCompat.getColor(imageView.context, R.color.green))
            }
        }

        @BindingAdapter("passDataToUpdateFragment")
        @JvmStatic
        fun passDataToUpdateFragment(cardView: MaterialCardView, todoItem: Todo){
            cardView.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(todoItem)
                cardView.findNavController().navigate(action)
            }
        }


    }
}