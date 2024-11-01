package br.com.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.libraryapp.repository.Repository
import br.com.libraryapp.room.BookEntity
import br.com.libraryapp.room.BooksDB
import br.com.libraryapp.ui.theme.LibraryAppTheme
import br.com.libraryapp.viewmodel.BooksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                Surface {
                    val mContext = LocalContext.current
                    val db = BooksDB.getInstance(mContext)
                    val repository = Repository(db)
                    val viewModel = BooksViewModel(repository)
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(model: BooksViewModel) {

    var inputBook by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputBook,
            onValueChange = { newBookValue ->
                inputBook = newBookValue
            },
            label = { Text(text = "Enter a book name") },
            placeholder = { Text(text= "Your book name") }
        )

        Button(onClick = {
            model.addBook(BookEntity(0, inputBook))
        }) {
            Text(text = "Insert book into DB")
        }
    }
}