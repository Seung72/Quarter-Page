package com.cholee.quarter_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cholee.quarter_page.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        setContentView(view)

        binding.moveToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            signIn(binding.etEmail.toString(), binding.etPassword.toString())
        }
    }


    public fun OnStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun moveMainPage(user:FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && email.contains("@")) {
            var userName = email.substring(0 until email.indexOf("@"))
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(binding.loginLayout, userName + "님 안녕하세요!" ,Snackbar.LENGTH_SHORT).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Snackbar.make(binding.loginLayout, "로그인에 실패 하였습니다.",Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}