package uz.gita.book_app_io.presentation.viewmodels

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.book_app_io.data.local.entity.BookEntity

interface BookDetailsViewModel {


    val bookFlow:SharedFlow<BookEntity>

    fun getBooksById(bookEntity: BookEntity)

    fun downloadBook(bookEntity: BookEntity)

    fun openReadBook(bookEntity: BookEntity)

}