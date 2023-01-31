package com.cholee.quarter_page

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cholee.quarter_page.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), BooksAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.imageViewpager.currentItem = binding.imageViewpager.currentItem + 1 }

    lateinit var bookList: ArrayList<Books>
    lateinit var booksAdapter: BooksAdapter

     lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firestore = FirebaseFirestore.getInstance()
        val view = binding.root
        setContentView(view)
        val pref = getSharedPreferences("checkFirst", MODE_PRIVATE)
        val checkFirst = pref.getBoolean("checkFirst", false)
        if (!checkFirst) {
            val editor = pref.edit()
            editor.putBoolean("checkFirst", true).commit()
            startActivity(Intent(this,OnBoardingActivity::class.java))
            finish()
        } else {
            // 첫 실행이 아닐 시 실행
        }

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

        bookList = ArrayList()
        booksAdapter = BooksAdapter(this, bookList)

        binding.rvHorizon.layoutManager = GridLayoutManager(this, 3)
        binding.rvHorizon.adapter = booksAdapter

        booksAdapter.onItemClickListener = this

//        val db = Firebase.firestore
//        val city = hashMapOf(
//            "name" to "Los Angeles",
//            "state" to "CA",
//            "country" to "USA"
//        )
//        db.collection("cities").document("LA")
//            .set(city)
//            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // R.id.home -> { startActivity(Intent(this, MainActivity::class.java)) }
                // R.id.my_library -> { startActivity(Intent(this, MyLibrary::class.java)) }
                R.id.settings -> { startActivity(Intent(this, SettingsActivity::class.java)) }
            }
            true
        }
    }
    companion object {
        const val TAG = "MyLog"
    }
    override fun onItemClick(books: Books) {
        var intent = Intent(this, BooksActivity::class.java)
        intent.putExtra("id", books.id)
        startActivity(intent)
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