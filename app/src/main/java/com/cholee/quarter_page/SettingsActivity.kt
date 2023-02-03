package com.cholee.quarter_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cholee.quarter_page.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        val user = auth!!.currentUser
        val userEmail = user!!.email
        setContentView(view)

        binding.btnLogout.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth?.signOut()
            finish()
        }
        binding.tvNickname.text = userEmail!!.substring(0 until userEmail.indexOf("@")) + "님"

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                // R.id.my_library -> { startActivity(Intent(this, MyLibrary::class.java)) }
                // R.id.settings -> { startActivity(Intent(this, SettingsActivity::class.java)) }
            }
            true
        }
    }
}