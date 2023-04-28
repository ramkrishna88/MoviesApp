package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.ui.Login.FragmentPageAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterFragment: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterFragment = FragmentPageAdapter(supportFragmentManager, lifecycle)
        binding.tabView.addTab(binding.tabView.newTab().setText("Login"))
        binding.tabView.addTab(binding.tabView.newTab().setText("SignUP"))

        binding.viewPager.adapter = adapterFragment

        binding.tabView.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    binding.viewPager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabView.selectTab(binding.tabView.getTabAt(position))
            }
        })
    }
}

