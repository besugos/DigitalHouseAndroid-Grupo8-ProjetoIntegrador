package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tfEmail: TextInputLayout
    private lateinit var tfPass: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPass: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_login)

        val btnLogin = this.findViewById<Button>(R.id.btnLoginLogin)
        val btnRegister = this.findViewById<Button>(R.id.btnCreateAccount)
        val btnGoogle = this.findViewById<Button>(R.id.btnGoogleLogin)
        val btnFacebook = this.findViewById<Button>(R.id.btnFacebookLogin)
        val toolbar = this.findViewById<androidx.appcompat.widget.Toolbar>(R.id.loginToolbar)

        tfEmail = findViewById<TextInputLayout>(R.id.tfEmailLogin)
        tfPass = findViewById<TextInputLayout>(R.id.tfPassLogin)
        etEmail = findViewById<TextInputEditText>(R.id.etEmailLogin)
        etPass = findViewById<TextInputEditText>(R.id.etPassLogin)


        setSupportActionBar(toolbar).apply {
            title = "Marvel Universe"
        }

        btnLogin.setOnClickListener() {

            if (validaCampos()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btnRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener() {
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
        }

        btnFacebook.setOnClickListener() {
            val intent = Intent(this, FacebookLoginActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(toolbar).apply {
            title = "Sign Up"
        }

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        etPass.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

    }

    private fun validaCampos(): Boolean {
        var response = true

        if (etEmail.text.isNullOrBlank()) {
            tfEmail.error = "Please type your e-mail"
            response = false
        }

        if (etPass.text.isNullOrBlank()) {
            tfPass.error = "Please type your password"
            response = false
        } else if (etPass.text!!.length < 8){
            tfPass.error = "Password must be at least 8 characters long"
            response = false
        }

        return response
    }
}