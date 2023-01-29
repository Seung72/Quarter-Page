package com.cholee.quarter_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cholee.quarter_page.databinding.ActivityOnboardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var context: Context
    private var dots: ArrayList<TextView> = arrayListOf<TextView>()
    private var layoutArr: Array<Int> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        context = this
        layoutArr = arrayOf(
            R.layout.onboarding_1,
            R.layout.onboarding_2,
            R.layout.onboarding_3
        )

        addTopDots(0)

        pagerAdapter = PagerAdapterInit()
        binding.vpOnboard.adapter = pagerAdapter
        binding.vpOnboard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                addTopDots(position)
                if (position == layoutArr.size - 1) {
                    binding.btnNext.text = "시작하기"
                } else {
                    binding.btnNext.text = "다음"
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
            fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?){}
        })

        binding.btnNext.setOnClickListener {
            var current: Int = getItem(+1)
            if (current < layoutArr.size) {
                binding.vpOnboard.currentItem = current
            } else {
            moveToMain()
            }
        }
        binding.btnPrevious.setOnClickListener {
            var current: Int = getItem(-1)
            if (current < layoutArr.size) {
                binding.vpOnboard.currentItem = current
            }
        }
    }
    private fun addTopDots(currentPage: Int) {

        val active = resources.getIntArray(R.array.array_dot_active)
        val inactive = resources.getIntArray(R.array.array_dot_inactive)

        binding.layoutDots.removeAllViews()
        for (i in layoutArr.indices) {
            dots.add(TextView(context))
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setPadding(5, 0, 5, 0)
            dots[i]!!.setTextColor(inactive[currentPage])
            binding.layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(active[currentPage])
    }
    private fun getItem(i: Int) = binding.vpOnboard.currentItem + i
    private fun moveToMain() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    inner class PagerAdapterInit : PagerAdapter() {

        private lateinit var layoutInflater: LayoutInflater
        private val aContext: Context = MyApplication.applicationContext()

        override fun getCount() = layoutArr.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = LayoutInflater.from(aContext)
            val view: View = layoutInflater.inflate(layoutArr[position], container, false)
            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}