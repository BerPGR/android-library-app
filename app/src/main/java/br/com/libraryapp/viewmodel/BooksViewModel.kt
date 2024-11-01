package br.com.libraryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.libraryapp.repository.Repository
import br.com.libraryapp.room.BookEntity
import kotlinx.coroutines.launch

class BooksViewModel(val repository: Repository): ViewModel() {

    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            repository.addBookToRoom(book)
        }
    }

    val books = repository.getAllBooks()
}