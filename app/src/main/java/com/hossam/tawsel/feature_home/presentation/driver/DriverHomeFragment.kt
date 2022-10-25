package com.hossam.tawsel.feature_home.presentation.driver

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.R.attr.colorOnSurface
import android.view.ViewGroup
import com.hossam.tawsel.R
import com.hossam.tawsel.databinding.FragmentDriverHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverHomeFragment : Fragment() {

    private var _binding: FragmentDriverHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDriverHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchMaterial.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                //TODO after finishing this screen design.. make extension fun that help to setSwitchButtonTrackTint
                binding.switchMaterial.trackTintList = ColorStateList.valueOf(resources.getColor(R.color.orange))
            } else {
                binding.switchMaterial.trackTintList = ColorStateList.valueOf(colorOnSurface)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}