package uz.gita.book_app_io.presentation.screens.details

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.ScreenBookDetailsBinding
import uz.gita.book_app_io.presentation.viewmodels.BookDetailsViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.BookDetailsViewModelImpl
import uz.gita.book_app_io.utils.hasPermissionApp

// Created by Jamshid Isoqov an 10/26/2022
@OptIn(FlowPreview::class)
@AndroidEntryPoint
class BookDetailsScreen : Fragment(R.layout.screen_book_details) {

    private val viewModel: BookDetailsViewModel by viewModels<BookDetailsViewModelImpl>()

    private val args: BookDetailsScreenArgs by navArgs()
    private var isDownload = false

    private val permissionList: List<String> by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        } else {
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }
    private val viewBinding: ScreenBookDetailsBinding by viewBinding()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var book = args.book
        viewBinding.apply {
            viewBinding.tvBookAuthor.text = book.author
            viewBinding.tvBookName.text = book.name
            viewBinding.tvBookPage.text = "${book.pages} pages"
            viewBinding.tvDescription.text = book.description
            Glide.with(requireContext())
                .load(book.imageUrl)
                .placeholder(R.drawable.ic_group)
                .into(imageBook)
        }

        viewBinding.btnDownloadOrReading
            .clicks().debounce(100L)
            .onEach {
                if (book.isDownload == 1) {
                    viewModel.openReadBook(book)
                } else {
                    hasPermissionApp(permissionList) {
                        if (it == 1) {
                            viewModel.downloadBook(book)
                            viewBinding.btnDownloadOrReading.text =
                                resources.getString(R.string.downloading)
                            viewBinding.progressDownload.visibility = View.VISIBLE
                            viewBinding.tvDownloadPercent.visibility = View.VISIBLE
                            viewBinding.btnDownloadOrReading.isClickable = false
                            viewLifecycleOwner.lifecycleScope.launch {
                                delay(3000)
                                viewBinding.btnDownloadOrReading.isClickable = true
                            }
                        }
                    }
                }
            }.launchIn(lifecycleScope)

        viewModel.bookFlow.onEach { booksData ->
            isDownload = booksData.isDownload == 1
            if (isDownload) {
                viewBinding.progressDownload.visibility = View.INVISIBLE
                viewBinding.tvDownloadPercent.visibility = View.INVISIBLE
                viewBinding.btnDownloadOrReading.apply {
                    text = resources.getString(R.string.start_reading)
                }
                book = booksData.copy()
            } else {
                viewBinding.progressDownload.progress = booksData.download
                viewBinding.tvDownloadPercent.text = "${booksData.download} %"
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.getBooksById(book)
    }

}