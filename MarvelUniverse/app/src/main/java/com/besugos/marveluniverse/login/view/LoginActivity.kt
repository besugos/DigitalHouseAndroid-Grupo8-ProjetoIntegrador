package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_login)

        val btnLogin = this.findViewById<Button>(R.id.btnLoginLogin)
        val btnRegister = this.findViewById<Button>(R.id.btnCreateAccount)
        val btnGoogle = this.findViewById<Button>(R.id.btnGoogleLogin)
        val btnFacebook = this.findViewById<Button>(R.id.btnFacebookLogin)
        val toolbar = this.findViewById<androidx.appcompat.widget.Toolbar>(R.id.loginToolbar)

        setSupportActionBar(toolbar).apply {
            title = "Marvel Universe"
        }

        btnLogin.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
    }
}