package com.mimdal.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mimdal.todo.data.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDAO

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getDatabaseInstance(context: Context): TodoDatabase {

            if (INSTANCE != null) {
                return INSTANCE!!

            } else {

                synchronized(this) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            TodoDatabase::class.java,
                            "todo_database"
                        )
                        .build()
                    return INSTANCE!!

                }
            }

        }
    }
}