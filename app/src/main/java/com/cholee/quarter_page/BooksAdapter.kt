package com.cholee.quarter_page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.api.ResourceProto.resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BooksAdapter(var context: Context, var booksList: ArrayList<Books>): RecyclerView.Adapter<BooksAdapter.ViewHolder>(){

    var onItemClickListener: OnItemClickListener? = null

    init {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("books").get().addOnSuccessListener { result ->
            for (snapshot in result) {
                booksList.add(snapshot.toObject(Books::class.java))
            }
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView!!){
        var ivBook: ImageView = itemView.findViewById(R.id.iv_book)
        var author: TextView = itemView.findViewById(R.id.tv_author)
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var price: TextView = itemView.findViewById(R.id.tv_price)
        var tag: TextView = itemView.findViewById(R.id.tv_tag)

        fun bind(book: Books) {
            Glide.with(context).load(book.imageUrl).into(ivBook)
            ivBook.setOnClickListener{
                if(onItemClickListener != null) onItemClickListener?.onItemClick(book)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(book: Books)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.books_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var book = booksList[position]
        holder.ivBook.layoutParams = LinearLayoutCompat.LayoutParams(300, 400)
        Glide.with(holder.itemView.context).load(booksList[position].imageUrl).into(holder.ivBook)
        holder.bind(book)
    }



}