package uz.gita.book_app_io.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.book_app_io.R
import uz.gita.book_app_io.databinding.ScreenMainBinding

// Created by Jamshid Isoqov an 10/26/2022
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewBinding: ScreenMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.pagerMain.adapter = MainPagerAdapter(requireActivity())
        viewBinding.pagerMain.isUserInputEnabled = false

        viewBinding.bottomNavBarMain.setOnItemSelectedListener {
            viewBinding.pagerMain.setCurrentItem(
                when (it.itemId) {
                    R.id.home_menu -> {
                        0
                    }
//                    R.id.saved_menu -> {
//                        1
//                    }
                    else -> {
                        1
                    }
                }, true
            )
            true
        }

    }


}