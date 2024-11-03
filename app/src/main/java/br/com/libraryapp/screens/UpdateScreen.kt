package br.com.libraryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.libraryapp.room.BookEntity
import br.com.libraryapp.viewmodel.BooksViewModel

@Composable
fun UpdateScreen(viewModel: BooksViewModel, bookId: String?, navController: NavHostController) {
    var inputBook by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Update the existing book", fontSize = 24.sp)
        OutlinedTextField(
            modifier = Modifier.padding(top=16.dp),
            value = inputBook,
            onValueChange = { newBookValue ->
                inputBook = newBookValue
            },
            label = {Text(text = "Update Book")},
            placeholder = {Text(text = "New book name")}
        )

        Button(onClick = {
            val newBook = BookEntity(bookId!!.toInt(), inputBook)
            viewModel.updateBook(newBook)
            navController.popBackStack()
        }) {
            Text(text = "Update Book")
        }
    }
}