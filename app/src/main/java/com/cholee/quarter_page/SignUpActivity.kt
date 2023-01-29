package com.cholee.quarter_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cholee.quarter_page.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        var view = binding.root
        setContentView(view)

        binding.btnSignUp.setOnClickListener {
            createAccount(binding.etEmail.toString(), binding.etPassword.toString(), binding.etPasswordVerify.toString())
        }
    }
    private fun createAccount(email: String, password: String, verify: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && verify.isNotEmpty()){
            //TODO: 가입 조건 생각해서 구현하기. 이메일 / 비밀번호 제약 등
        } else {
            Snackbar.make(binding.moveToLogin, "입력 칸이 비어있습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }
}