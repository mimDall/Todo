package com.mimdal.todo.viewModel

import android.app.Application
import android.graphics.Color.red
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.mimdal.todo.R
import com.mimdal.todo.adapter.CustomSpinner
import com.mimdal.todo.data.TodoDatabase
import com.mimdal.todo.data.model.Priority
import com.mimdal.todo.data.model.SpinnerPriority
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.fragments.UpdateFragmentArgs
import com.mimdal.todo.repository.TodoRepository
import com.mimdal.todo.util.Util
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = TodoDatabase.getDatabaseInstance(application).todoDao()
    private val repository: TodoRepository = TodoRepository(toDoDao)
    val getAllData: LiveData<List<Todo>> = repository.getAllData
    val searchQuery = MutableLiveData<String>(null)


    val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {

            parent?.setSelection(position)


//            when ((adapter?.selectedView as TextView).text) {
//
//                "Low Priority" -> (adapter.selectedView as TextView).setTextColor(
//                    ContextCompat.getColor(application, R.color.green)
//                )
//
//                "Medium Priority" -> (adapter.selectedView as TextView).setTextColor(
//                    ContextCompat.getColor(application, R.color.yellow)
//                )
//                "High Priority" -> (adapter.selectedView as TextView).setTextColor(
//                    ContextCompat.getColor(application, R.color.red)
//                )
//
//            }
//            when (position) {
//                0 -> {
//                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
//                        ContextCompat.getColor(
//                            application,
//                            R.color.red
//                        )
//                    )
//                }
//                1 -> {
//                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
//                        ContextCompat.getColor(
//                            application,
//                            R.color.yellow
//                        )
//                    )
//                }
//                2 -> {
//                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
//                        ContextCompat.getColor(
//                            application,
//                            R.color.green
//                        )
//                    )
//                }
//            }


        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }


    fun insertData(todo: Todo) {
        viewModelScope.launch(IO) {
            repository.insertData(todo)
        }
    }

    fun updateData(todo: Todo) {
        viewModelScope.launch(IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteData(todo: Todo) {
        viewModelScope.launch(IO) {
            repository.deleteTodo(todo)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(IO) {
            repository.deleteAllData()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Todo>> {
        return repository.searchDatabase(searchQuery)
    }


    fun verifyUserData(title: String, description: String): Boolean {
        return (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description))
    }

    fun convertStringToPriority(mPriority: String): Priority {
        return when (mPriority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}