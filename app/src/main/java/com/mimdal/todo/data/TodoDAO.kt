package com.mimdal.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mimdal.todo.data.model.Todo

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToDo (todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM todo_table WHERE description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<Todo>>
}