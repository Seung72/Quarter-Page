package com.cholee.quarter_page

import android.content.Intent
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

        // 회원가입 버튼 클릭
        binding.btnSignUp.setOnClickListener {
            createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString(), binding.etPasswordVerify.text.toString())
        }

        // 로그인 페이지로 이동
        binding.moveToLogin.setOnClickListener {
            finish()
        }
    }
    // 이메일 비밀번호 제약 확인
    private fun createAccount(email: String, password: String, verify: String) {
        var msg = ""
        if (email.isNotEmpty() || password.isNotEmpty() || verify.isNotEmpty()){
            if (!email.contains("@")) {
                msg = "잘못된 형식의 이메일 주소입니다."
            } else if (password.length !in 8..20) {
                msg = "비밀번호는 8자리에서 20자리로 입력해야합니다."
            } else if (password != verify) {
                msg = "비밀번호 확인과 일치하지 않습니다."
            } else {
                auth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            msg = "계정이 생성되었습니다!"
                            finish()
                        } else {
                            msg = "계정 생성에 문제가 있습니다."
                        }
                    }
                }
        } else {
           msg ="비어있는 칸이 있습니다."
        }
        if (msg != "") Snackbar.make(binding.moveToLogin, msg, Snackbar.LENGTH_SHORT).show()
    }
}