package br.com.libraryapp.repository

import br.com.libraryapp.room.BookEntity
import br.com.libraryapp.room.BooksDB

class Repository(val bookDB: BooksDB) {

    suspend fun addBookToRoom(bookEntity: BookEntity) {
        bookDB.bookDao().addBook(bookEntity)
    }

    fun getALlBooks() = bookDB.bookDao().getAllBooks()
}