package br.com.libraryapp.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//NOME DA TABELA
@Entity
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    //@ColumnInfo(name = "book_title")
    val title: String
)
