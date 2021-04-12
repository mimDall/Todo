package com.mimdal.todo.repository

import androidx.lifecycle.LiveData
import com.mimdal.todo.data.model.Todo
import com.mimdal.todo.data.TodoDAO

class TodoRepository(
    private val todoDAO: TodoDAO
) {


    val getAllData: LiveData<List<Todo>> = todoDAO.getAllData()

    suspend fun insertData(todo: Todo) {
        todoDAO.insertToDo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDAO.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDAO.deleteTodo(todo)
    }

    suspend fun deleteAllData() {
        todoDAO.deleteAllData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Todo>> {
       return todoDAO.searchDatabase(searchQuery)
    }
}