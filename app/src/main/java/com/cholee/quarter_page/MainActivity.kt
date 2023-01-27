package com.cholee.quarter_page

import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cholee.quarter_page.databinding.ActivityMainBinding
import com.cholee.quarter_page.databinding.ToolbarBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.imageViewpager.currentItem = binding.imageViewpager.currentItem + 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imageList = scrollImgList()
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.imageViewpager.apply {
            adapter = ViewPagerAdapter(imageList, binding.imageViewpager)
            offscreenPageLimit = 1
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderImageHandler.removeCallbacks(sliderImageRunnable)
                    sliderImageHandler.postDelayed(sliderImageRunnable, 5000)
                }
            })
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }
        }




    }

    override fun onResume() {
        super.onResume()
        sliderImageHandler.postDelayed(sliderImageRunnable, 5000)
    }

    override fun onPause() {
        super.onPause()
        sliderImageHandler.removeCallbacks(sliderImageRunnable)
    }

    // 스크롤에 사용할 이미지를 리스트화하여 반환한다.
    // TODO: 네트워크 상에서 받아올 수 있게 구현 필요
    private fun scrollImgList(): ArrayList<Int> {
        val imageList = arrayListOf<Int>().apply {
            for (i in 0..2) {
                add(R.drawable.img_scroll_test)
                add(R.drawable.test)
            }
        }
        return imageList
    }
}