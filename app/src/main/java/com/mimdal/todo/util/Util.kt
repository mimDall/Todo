package com.mimdal.todo.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.mimdal.todo.R
import com.mimdal.todo.data.model.SpinnerPriority

class Util{

    companion object{
        fun closeKeyboard(view: View) {
            val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }


        fun createSpinnerList():ArrayList<SpinnerPriority>{

            val spinner = ArrayList<SpinnerPriority>()

            spinner.add(SpinnerPriority("High Priority", R.color.red))
            spinner.add(SpinnerPriority("Medium Priority", R.color.yellow))
            spinner.add(SpinnerPriority("Low Priority", R.color.green))

            return spinner

        }
    }

}
