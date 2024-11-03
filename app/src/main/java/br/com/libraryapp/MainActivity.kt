package br.com.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.libraryapp.repository.Repository
import br.com.libraryapp.room.BookEntity
import br.com.libraryapp.room.BooksDB
import br.com.libraryapp.screens.UpdateScreen
import br.com.libraryapp.ui.theme.LibraryAppTheme
import br.com.libraryapp.viewmodel.BooksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val mContext = LocalContext.current
                    val db = BooksDB.getInstance(mContext)
                    val repository = Repository(db)
                    val viewModel = BooksViewModel(repository)

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable(route = "MainScreen") {
                            MainScreen(model = viewModel, navController = navController)
                        }

                        composable(route = "UpdateScreen/{bookId}") {
                            val bookId = it.arguments?.getString("bookId")
                            UpdateScreen(viewModel = viewModel, bookId = bookId, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(model: BooksViewModel, navController: NavHostController) {

    var inputBook by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 22.dp, start = 6.dp, end = 6.dp)
    ) {
        Text("Insert books in ROOM DB", fontSize = 22.sp)

        OutlinedTextField(
            value = inputBook,
            onValueChange = { newBookValue ->
                inputBook = newBookValue
            },
            label = { Text(text = "Enter a book name") },
            placeholder = { Text(text= "Your book name") }
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            ),
            onClick = {
                model.addBook(BookEntity(0, inputBook))
            }
        ) {
            Text(text = "Insert book into DB")
        }
        
        BooksList(viewModel = model, navController = navController)
    }
}

@Composable
fun BookCard(viewmodel: BooksViewModel, book: BookEntity, navController: NavHostController) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "" + book.id, fontSize = 24.sp,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                color = Color.Blue
            )

            Text(text = book.title, fontSize = 24.sp,
                modifier = Modifier.fillMaxSize(0.7f)
                )

            Row(
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { viewmodel.deleteBook(book) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }

                IconButton(onClick = {
                    navController.navigate("UpdateScreen/${book.id}")
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Update")
                }
            }

        }
    }
}

@Composable
fun BooksList(viewModel: BooksViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "My Library", fontSize = 24.sp, color = Color.Red)

        LazyColumn {
            items(books) {
                    book ->
                BookCard(viewmodel = viewModel, book = book, navController = navController)
            }
        }
    }
}