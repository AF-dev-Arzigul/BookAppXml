package uz.gita.book_app_io.presentation.screens.main.pages.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.PageSavedBinding
import uz.gita.book_app_io.presentation.viewmodels.SavedViewModel
import uz.gita.book_app_io.presentation.viewmodels.impl.SavedViewModelImpl

@AndroidEntryPoint
class SavedPage : Fragment(R.layout.page_saved) {

    private val viewModel: SavedViewModel by viewModels<SavedViewModelImpl>()

    private val viewBinding: PageSavedBinding by viewBinding()

    private val adapter: SavedAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SavedAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listBooks.adapter = adapter

        adapter.setItemClickListener {
            viewModel.openBookDetails(it)
        }

        viewModel.getAllSavedBooks.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


}