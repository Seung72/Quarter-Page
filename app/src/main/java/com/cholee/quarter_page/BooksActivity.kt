package com.cholee.quarter_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cholee.quarter_page.databinding.ActivityBooksBinding
import com.google.firebase.firestore.FirebaseFirestore


class BooksActivity : AppCompatActivity() {

    lateinit var binding: ActivityBooksBinding
    lateinit var fireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        var view = binding.root
        var id = intent.getStringExtra("id")
        var pdf: String = ""
        setContentView(view)

        fireStore = FirebaseFirestore.getInstance()

        if(id != null) {
            fireStore.collection("books").document("economy" + (id.toInt()+1).toString()).get().addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    var books = task.result?.toObject(Books::class.java)
                    Glide.with(this).load(books?.imageUrl).into(binding.ivBookImg)
                    binding.tvTitle.text = books?.title
                    binding.tvAuthor.text = books?.author
                    binding.tvPrice.text = books?.price.toString()
                    binding.tvMainText.text = books?.text
                    pdf = books?.pdfUrl.toString()
                } else {
                    binding.tvTitle.text = "오류"
                }
            }
        }
        binding.btnPurchase.setOnClickListener{
            openPdf(pdf)
        }
    }

    private fun openPdf(pdfUrl: String) {

        var uri: Uri = Uri.parse(pdfUrl)
        var intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}