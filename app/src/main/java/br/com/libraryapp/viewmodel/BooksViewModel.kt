package br.com.libraryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.libraryapp.repository.Repository
import br.com.libraryapp.room.BookEntity
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: Repository): ViewModel() {

    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            repository.addBookToRoom(book)
        }
    }

    val books = repository.getAllBooks()

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            repository.deleteBooksFromRoom(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBookFromRoom(book)
        }
    }
}