package com.example.glossaryadmin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.glossaryadmin.UserFragment.Category_Fragment
import com.example.glossaryadmin.UserFragment.Home_Fragment
import com.example.glossaryadmin.UserFragment.offer_Fragment
import com.example.glossaryadmin.UserFragment.profile_Fragment
import com.example.glossaryadmin.User_Activity.UHomePage

class FrgamentAdapter(UHomePage: UHomePage, supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager)
{
    override fun getCount(): Int {
        return 2;
    }
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Home_Fragment()
            1 -> Category_Fragment()
            2 -> offer_Fragment()
            else -> profile_Fragment()
        }
    }
}