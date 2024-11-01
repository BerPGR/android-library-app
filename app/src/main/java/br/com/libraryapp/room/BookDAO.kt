package br.com.libraryapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDAO {

    @Insert
    suspend fun addBook(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getAllBooks(): Flow<List<BookEntity>>
}