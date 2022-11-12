package com.hossam.tawsel.feature_language.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hossam.tawsel.R
import com.hossam.tawsel.core.Nav
import com.hossam.tawsel.core.SharedPref
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.core.navigate
import com.hossam.tawsel.core.onClick
import com.hossam.tawsel.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(FragmentLanguageBinding::inflate) {

    private var isArabic = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
    }

    private fun onClicks(){
        binding.apply {

            cvArabic.onClick {
                SharedPref.setUserLanguage(isArabic)
                ivRbAr.setImageResource(R.drawable.ic_checked)
                ivRbEng.setImageResource(R.drawable.ic_unchecked)
            }

            cvEnglish.onClick {
                SharedPref.setUserLanguage(!isArabic)
                ivRbEng.setImageResource(R.drawable.ic_checked)
                ivRbAr.setImageResource(R.drawable.ic_unchecked)
            }

            btnNext.onClick {
                navigate(Nav.LANGUAGE_TO_LOGIN)
            }
        }
    }





}