package com.example.glossaryadmin.UserFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.glossaryadmin.R
import com.example.glossaryadmin.databinding.FragmentOfferBinding
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class offer_Fragment : Fragment() {

    lateinit var binding: FragmentOfferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOfferBinding.inflate(layoutInflater)
        imageSlider()
        return binding.root
    }

    fun imageSlider() {

        binding.imageSlider.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.one,
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.two
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.three
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.for4
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.five
            )
        )

        binding.imageSlider.setData(list)
    }

}