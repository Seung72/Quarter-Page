package com.cholee.quarter_page

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cholee.quarter_page.databinding.ActivityBooksBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BooksActivity : AppCompatActivity() {

    lateinit var binding: ActivityBooksBinding
    lateinit var fireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        var view = binding.root
        var id = intent.getStringExtra("id")
        setContentView(view)

        fireStore = FirebaseFirestore.getInstance()

        if(id != null) {
            fireStore.collection("book").document(id).get().addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    var books = task.result?.toObject(Books::class.java)
                    Glide.with(this).load(books?.imageUrl).into(binding.ivBookImg)
                    binding.tvTitle.text = books?.title
                    binding.tvAuthor.text = books?.author
                    binding.tvPrice.text = books?.price.toString()
                }
            }
        }
    }
}